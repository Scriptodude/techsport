import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import AddTimeBody from '../models/addTimeBody';
import addTimeBody from '../models/addTimeBody';
import Team, { createDefaultTeam } from '../models/team';
import { TeamsService } from '../teams.service';
import { catchError } from 'rxjs/operators'
import { HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';
import { Activity } from '../models/activity';
import { ActivityService } from '../activity.service';
import { ActivatedRoute } from '@angular/router';
import ComponentMessage from '../models/componentMessage';

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
  public error: string = '';
  public success: string = '';
  private resetErrorTimeout: any;

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
    const d: Date = new Date();
    d.setTime(d.getTime() + 1 * 24 * 60 * 60 * 1000);
    const expires: string = `expires=${d.toUTCString()}`;
    document.cookie = `token=${this.tokenInput}; ${expires}`;

    this.checkAdmin();
  }

  handleMessage(message: ComponentMessage) {
    this.success = '';
    this.error = '';
    if (message.message.trim() == '') return;

    switch(message.isSuccess) {
      case true:
        this.success = message.message;
        break;
      case false:
        this.error = message.message;
        break;
    }

    if (this.resetErrorTimeout) window.clearTimeout(this.resetErrorTimeout)
    this.resetErrorTimeout = window.setTimeout(() => {
      this.error = '';
      this.success = '';
    }, 3000)
  }

  private checkAdmin() {
    this.loginService.isAdmin().subscribe(r => this.isAdmin = r.status == 200)
  }
}
