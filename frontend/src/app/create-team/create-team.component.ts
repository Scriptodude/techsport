import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Team from '../models/team';
import ComponentMessage from '../models/componentMessage';
import { TeamsService } from '../teams.service';

@Component({
  selector: 'app-create-team',
  templateUrl: './create-team.component.html',
  styleUrls: ['./create-team.component.scss']
})
export class CreateTeamComponent implements OnInit {

  public newTeam: { name: string; members: string; } = { name: '', members: '' };
  @Output() message = new EventEmitter<ComponentMessage>()
  @Output() createdTeam = new EventEmitter<Team>();

  constructor(private teamService: TeamsService) { }

  ngOnInit(): void {
  }

  createTeam() {
    const name = this.newTeam.name.trim();
    const members = this.newTeam.members.trim().split('\n');

    if (name.length != 0) {
      this.teamService.createTeam({ name, members })
        .pipe(catchError(this.handleErrorCreateTeam.bind(this)))
        .subscribe(r => {
          if (r == null) return;
          this.createdTeam.emit(r);
          this.newTeam = { name: '', members: '' }
          this.message.emit(new ComponentMessage(true, "Nouvelle équipe ajoutée."));
        });
    } else {
      this.message.emit(new ComponentMessage(false, "La nouvelle équipe doit avoir un nom."))
    }
  }

  private handleErrorCreateTeam(error: HttpErrorResponse) {
    switch (error.status) {
      case 409:
        this.message.emit(new ComponentMessage(false, 'Une équipe avec ce nom existe déjà'));
        break;
      case 404:
        this.message.emit(new ComponentMessage(false, 'Cette équipe n\'existe pas'));
        break;
      default:
        this.message.emit(new ComponentMessage(false, 'Erreur lors de la création de l\'équipe'));
        break;
    }

    throwError("Could not finish the call");
    return of(null);
  }
}
