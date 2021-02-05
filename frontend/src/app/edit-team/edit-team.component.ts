import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import ComponentMessage from '../models/componentMessage';
import Team, { createDefaultTeam } from '../models/team';
import { TeamsService } from '../teams.service';

@Component({
  selector: 'app-edit-team',
  templateUrl: './edit-team.component.html',
  styleUrls: ['./edit-team.component.scss']
})
export class EditTeamComponent implements OnInit {

  @Input() teams: Team[] = [];
  @Output() message = new EventEmitter<ComponentMessage>();
  public selectedTeam: Team = createDefaultTeam();
  public members: String = '';

  constructor(private teamService: TeamsService) { }

  ngOnInit(): void {
  }

  selectTeam(team: Team) {
    this.selectedTeam = team

    if (team.members.length > 0) {
      this.members = team.members.join('\n')
    }
  }

  editTeam() {
  
    if (this.selectedTeam.name.trim() == '') {
      this.message.emit(new ComponentMessage(false, 'Vous devez choisir une équipe'));
      return;
    }

    this.teamService
      .updateTeam(this.selectedTeam.name, { members: this.members.length == 0 ? [] : this.members.split('\n') })
      .pipe(catchError(this.handleError.bind(this)))
      .subscribe(r => {
        if (r == null) return
        this.message.emit(new ComponentMessage(true, 'Équipe mise à jour avec succès.'));
        this.selectedTeam = createDefaultTeam();
        this.members = '';
      })
  }

  private handleError(error: HttpErrorResponse) {
    switch (error.status) {
      case 409:
        this.message.emit(new ComponentMessage(false, 'Une équipe avec ce nom existe déjà'));
        break;
      case 404:
        this.message.emit(new ComponentMessage(false, 'Cette équipe n\'existe pas'));
        break;
      default:
        this.message.emit(new ComponentMessage(false, 'Erreur lors de la modification de l\'équipe'));
        break;
    }

    throwError("Could not finish the call");
    return of(null);
  }

}
