import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import addTimeBody from '../models/addTimeBody';
import ComponentMessage from '../models/componentMessage';
import Team, { createDefaultTeam } from '../models/team';
import { TeamsService } from '../teams.service';

@Component({
  selector: 'app-add-time',
  templateUrl: './add-time.component.html',
  styleUrls: ['./add-time.component.scss']
})
export class AddTimeComponent implements OnInit {

  @Input() teams: Team[] = []
  @Output() message = new EventEmitter<ComponentMessage>()
  public selectedTeam: Team = createDefaultTeam();
  public moreTime: addTimeBody = { hours: 0, minutes: 0, seconds: 0, who: '', why: '', athlete: '' }

  constructor(private teamService: TeamsService) { }

  ngOnInit(): void {
  }

  selectTeam(team: Team) {
    this.selectedTeam = team
  }

  addTime() {
    if (this.selectedTeam.name.trim() == '') {
      this.message.emit(new ComponentMessage(false, 'Vous devez choisir une équipe.'));
      return;
    }

    if (this.moreTime.hours == 0 && this.moreTime.minutes == 0 && this.moreTime.seconds == 0) {
      this.message.emit(new ComponentMessage(false, 'Vous devez fournir soit les heures, les minutes ou les secondes.'));
      return;
    }

    if (this.moreTime.who.trim() == '') {
      this.message.emit(new ComponentMessage(false, 'Veuillez entrer le nom de l\'approbateur'));
      return;
    }

    if (this.moreTime.athlete.trim() == '') {
      this.message.emit(new ComponentMessage(false, 'Veuillez entrer le nom de l\'athlète.'));
      return;
    }

    if (this.moreTime.why.trim() == '') {
      this.message.emit(new ComponentMessage(false, 'Veuillez entrer une courte description de l\'activité'));
      return;
    }

    this.teamService.addTime(this.selectedTeam.name, this.moreTime)
      .pipe(catchError(this.handleError.bind(this)))
      .subscribe(r => {
        if (r == false) return;
        this.selectedTeam.timeTotal.hours += this.moreTime.hours;
        this.selectedTeam.timeTotal.minutes += this.moreTime.minutes;
        this.selectedTeam.timeTotal.seconds += this.moreTime.seconds;
        this.message.emit(new ComponentMessage(true, 'Temps mis à jour avec succès.'));
      });
  }

  private handleError(error: HttpErrorResponse) {
    this.message.emit(new ComponentMessage(false, error.status == 404 ? 'L\'équipe n\'existe pas' : 'Erreur lors de la mise à jour du temps'));

    throwError("Could not finish the call");
    return of(false)
  }
}
