import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ActivityService } from '../activity.service';
import { Activity } from '../models/activity';
import ComponentMessage from '../models/componentMessage';
import Team from '../models/team';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss']
})
export class ActivitiesComponent implements OnInit {

  public activities: Activity[] = [];
  public filter: string = 'À Valider';
  public teamFilter: string | null = null;
  public pageCount: number = 1;
  @Output() message = new EventEmitter<ComponentMessage>()
  @Input() teams: Team[] = []
  public page: number = 1;

  constructor(private activityService: ActivityService) { }

  ngOnInit(): void {
    this.getActivities();
    this.teamFilter = this.teams[0]?.name
  }

  pages() {
    return Array(this.pageCount)
  }

  changePage(page: number) {
    this.page = page
    this.getActivities(this.filter)
  }

  getStatus(activity: Activity) {
    if (activity == null) return ''
    if (activity.approved === true) return 'Status: Approuvée pour l\'équipe ' + activity.teamName || 'Inconnue';
    else if (activity.approved === false) return 'Status: Refusée'
    return 'Status: À Valider'
  }

  getActivities(filter: string = 'À Valider', team: string | null = null) {
    this.filter = filter;
    this.teamFilter = team;

    switch (this.filter) {
      case "Toutes":
        this.activityService.getAllActivities(this.page, null, true, team).subscribe(a => {this.activities = a. activities; this.pageCount = a.pages});
        break;
      case 'Refusées':
        this.activityService.getAllActivities(this.page, false, false, team).subscribe(a => {this.activities = a.activities; this.pageCount = a.pages});
        break;
      case 'Approuvées':
        this.activityService.getAllActivities(this.page, true, false, team).subscribe(a => {this.activities = a.activities; this.pageCount = a.pages});
        break;
      default:
      case 'À Valider':
        this.activityService.getAllActivities(this.page, null, false, team).subscribe(a => {this.activities = a.activities; this.pageCount = a.pages});
        break;
    }
  }

  changeApproval(activity: Activity, team: string, approved: boolean) {

    if (activity == null || activity.id.trim().length == 0) {
      this.message.emit(new ComponentMessage(false, 'Aucune activité associée'));
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

        this.message.emit(new ComponentMessage(true, 'La demande fut ' + (approved == true ? 'approuvée' : 'refusée') + ' avec succès!'));
        this.getActivities()
      })
  }

  getRefusalName(activity: Activity) {
    return activity.approved === true ? 'Enlever les points' : 'Refuser';
  }

  getApprovalName(activity: Activity) {
    return activity.approved === false || activity.approved === null ? 'Approuver pour l\'équipe' : 'Transférer vers l\'équipe'
  }

  private handleAppovalError(error: HttpErrorResponse) {
    switch (error.status) {
      case 409:
        this.message.emit(new ComponentMessage(false, 'Cette demande est déjà approuvée.'));
        this.getActivities();
        break;
      case 404:
        this.message.emit(new ComponentMessage(false, 'Cette demande n\'existe plus.'));
        break;
      default: this.message.emit(new ComponentMessage(false, 'Erreur lors de l\'approbation de la demande.'));
    }

    throwError("Could not finish the call")
    return of(false)
  }

}
