import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import AddTimeBody from '../models/addTimeBody';
import addTimeBody from '../models/addTimeBody';
import Team, { createDefaultTeam } from '../models/team';
import { TeamsService } from '../teams.service';
import { catchError } from 'rxjs/operators'
import { HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';
import Activity from '../models/activity';
import { ActivityService } from '../activity.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  public active = 1;
  public activities: Activity[] = [];
  public tokenInput: string = '';
  public isAdmin: Boolean = false;
  public teams: Team[] = [];
  public newTeam: { name: string; members: string; } = { name: '', members: '' };
  public moreTime: addTimeBody = { hours: 0, minutes: 0, seconds: 0 }
  public selectedTeam: Team = createDefaultTeam();
  public error: string = '';
  public success: string = '';
  public filter: string = 'todo';
  public page: number = 1;
  public pageCount: number = 1;

  constructor(
    private loginService: LoginService,
    private teamService: TeamsService,
    private activityService: ActivityService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.checkAdmin();

    this.teamService.getAllTeams().subscribe(t => this.teams = t);
    this.activatedRoute.queryParams.subscribe(params => this.page = params['page'] || 1)
    this.getActivities();
  }

  async submitToken() {
    this.error = '';
    this.success = '';
    this.isAdmin = false;
    const d: Date = new Date();
    d.setTime(d.getTime() + 1 * 24 * 60 * 60 * 1000);
    const expires: string = `expires=${d.toUTCString()}`;
    document.cookie = `token=${this.tokenInput}; ${expires}`;

    this.checkAdmin();
  }

  selectTeam(team: Team) {
    this.error = '';
    this.success = '';
    this.selectedTeam = team

    if (team.members.length > 0) {
      this.newTeam.members = team.members.join('\n')
    }
  }

  createTeam() {
    this.error = '';
    this.success = '';
    const name = this.newTeam.name.trim();
    const members = this.newTeam.members.trim().split('\n');

    if (name.length != 0) {
      this.teamService.createTeam({ name, members })
        .pipe(catchError(this.handleErrorCreateTeam.bind(this)))
        .subscribe(r => {
          if (r == null) return;
          this.teams.push(r);
          this.newTeam = { name: '', members: '' }
          this.success = 'Nouvelle équipe ajoutée.';
        });
    }
  }

  addTime() {
    this.error = '';
    this.success = '';
    if (this.moreTime.hours == 0 && this.moreTime.minutes == 0 && this.moreTime.seconds == 0) {
      this.error = 'Vous devez fournir soit les heures, les minutes ou les secondes';
      return;
    }

    if (this.selectedTeam.name.trim() == '') {
      this.error = 'Vous devez choisir une équipe';
      return;
    }

    this.teamService.addTime(this.selectedTeam.name, this.moreTime)
      .pipe(catchError(this.handleError.bind(this)))
      .subscribe(r => {
        if (r == false) return;
        this.selectedTeam.timeTotal.hours += this.moreTime.hours;
        this.selectedTeam.timeTotal.minutes += this.moreTime.minutes;
        this.selectedTeam.timeTotal.seconds += this.moreTime.seconds;
        this.success = 'Temps mis à jour avec succès.';
      });
  }

  editTeam() {
    this.error = '';
    this.success = '';

    if (this.selectedTeam.name.trim() == '') {
      this.error = 'Vous devez choisir une équipe';
      return;
    }

    this.teamService
      .updateTeam(this.selectedTeam.name, { members: this.newTeam.members.length == 0 ? [] : this.newTeam.members.split('\n') })
      .pipe(catchError(this.handleErrorCreateTeam.bind(this)))
      .subscribe(r => {
        if (r == null) return
        this.success = 'Équipe mise à jour avec succès.';
        this.error = '';
        this.selectedTeam = createDefaultTeam();
        this.newTeam = { name: '', members: '' }
      })
  }

  changeApproval(activity: Activity, team: string, approved: boolean) {
    this.error = '';
    this.success = '';

    if (activity == null || activity.id.trim().length == 0) {
      this.error = 'Aucune activité associée';
      return
    }

    this.activityService.changeActivityApproval({
      teamName: team,
      activityId: activity.id,
      approved
    })
      .pipe(catchError(this.handleAppovalError.bind(this)))
      .subscribe(r => {
        if (r == false) return;

        this.success = 'La demande fut ' + (approved == true ? 'approuvée' : 'refusée') + ' avec succès!';
        this.getActivities()
      })
  }

  getActivities(filter: string = 'todo') {
    this.activityService.getPageCount().subscribe(p => this.pageCount = p.pages)

    switch(filter) {
      case "all":
        this.activityService.getAllActivities(this.page).subscribe(a => this.activities = a);
        this.filter = 'Toutes';
        break;
      case 'declined':
        this.activityService.getAllActivities(this.page).subscribe(a => this.activities = a.filter(i => i.approved == false));
        this.filter = 'Refusées';
        break;    
      case 'approved':
        this.activityService.getAllActivities(this.page).subscribe(a => this.activities = a.filter(i => i.approved == true));
        this.filter = 'Approuvées';
        break;
      default:
      case 'todo':
        this.activityService.getActivitiesToValidate(this.page).subscribe(a => this.activities = a);
        this.filter = 'À Valider';
        break;
    }
  }

  getStatus(activity: Activity) {
    if (activity == null) return ''
    if (activity.approved === true) return 'Status: Approuvée pour l\'équipe ' + activity.teamName || 'Inconnue';
    else if (activity.approved === false) return 'Status: Refusée'
    return 'Status: À Valider'
  }

  pages() {
    return Array(this.pageCount)
  }

  private checkAdmin() {
    this.loginService.isAdmin().subscribe(r => this.isAdmin = r.status == 200)
  }

  private handleError(error: HttpErrorResponse) {
    this.error = error.status == 404 ? 'L\'équipe n\'existe pas' : 'Erreur lors de la mise à jour du temps';

    throwError("Could not finish the call");
    return of(false)
  }

  private handleErrorCreateTeam(error: HttpErrorResponse) {
    switch (error.status) {
      case 409:
        this.error = 'Une équipe avec ce nom existe déjà'
        break;
      case 404:
        this.error = 'Cette équipe n\'existe pas'
        break;
      default: this.error = 'Erreur lors de la création/édition de l\'équipe'
    }

    throwError("Could not finish the call");
    return of(null);
  }

  private handleAppovalError(error: HttpErrorResponse) {
    switch (error.status) {
      case 409:
        this.error = 'Cette demande est déjà approuvée'
        this.getActivities();
        break;
      case 404:
        this.error = 'Cette demande n\'existe plus'
        break;
      default: this.error = 'Erreur lors de l\'approbation de la demande'
    }

    throwError("Could not finish the call")
    return of(false)
  }
}
