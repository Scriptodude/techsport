import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import AddTimeBody from '../models/addTimeBody';
import addTimeBody from '../models/addTimeBody';
import Team from '../models/team';
import { TeamsService } from '../teams.service';
import { catchError } from 'rxjs/operators'
import { HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  public active = 1;
  public tokenInput: string = '';
  public isAdmin: Boolean = false;
  public teams: Team[] = [];
  public newTeam: {name: string; members: string;} = { name: '', members: '' };
  public moreTime: addTimeBody = {hours: 0, minutes:0, seconds: 0}
  public selectedTeam: {name: string, members: string[], currentTime: AddTimeBody} = {name: '', members: [], currentTime: this.moreTime};
  public error: string = '';
  public success: string = '';

  constructor(
    private loginService: LoginService,
    private teamService: TeamsService) { }

  ngOnInit(): void {
    this.checkAdmin();

    this.teamService.getAllTeams().subscribe(t => this.teams = t);
  }

  async submitToken() {
    this.error = '';
    this.success = '';
    this.isAdmin = false;
    const d:Date = new Date();
    d.setTime(d.getTime() + 1 * 24 * 60 * 60 * 1000);
    const expires:string = `expires=${d.toUTCString()}`;
    document.cookie = `token=${this.tokenInput}; ${expires}`;

    this.checkAdmin();
  }

  selectTeam(name: string, currentTime: AddTimeBody,  members: string[] = []) {
    this.error = '';
    this.success = '';
    this.selectedTeam = {name, members, currentTime};

    if(members.length > 0) {
      this.newTeam.members = members.join('\n')
    }
  }

  createTeam() {
    this.error = '';
    this.success = '';
    const name = this.newTeam.name.trim();
    const members = this.newTeam.members.trim().split('\n');

    if (name.length != 0) {
      this.teamService.createTeam({name, members})
      .pipe(catchError(this.handleErrorCreateTeam.bind(this)))
      .subscribe(r => {
        if (r == null) return;
        this.teams.push(r);
        this.newTeam = {name: '', members: ''}
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
        this.selectedTeam.currentTime.hours += this.moreTime.hours;
        this.selectedTeam.currentTime.minutes += this.moreTime.minutes;
        this.selectedTeam.currentTime.seconds += this.moreTime.seconds;
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
        this.selectedTeam = {name:'', members:[], currentTime: {hours: 0, minutes: 0, seconds: 0}}
        this.newTeam = {name:'', members: ''}
      })
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
    switch(error.status) {
      case 409:
        this.error = 'Une équipe avec ce nom existe déjà'
        break;
      case 404:
        this.error = 'Cette équipe n\'existe pas'
        break;
      default: this.error = 'Erreur lors de la création/édition de l\'équipe'
    }
    this.error = error.status == 409 ? 'Une équipe avec ce nom existe déjà' : 'Erreur lors de la création de l\'équipe'

    throwError("Could not finish the call");
    return of(null);
  }
}
