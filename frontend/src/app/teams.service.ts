import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import AddTimeBody from './models/addTimeBody';
import CreateTeamBody from './models/createTeamBody';
import Team from './models/team';

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  constructor(private client: HttpClient) { }

  getAllTeams() {
    return this.client.get<Team[]>(environment.apiUrl + "/teams", {withCredentials: true});
  }

  createTeam(body: CreateTeamBody) {
    return this.client.post<Team>(environment.apiUrl + "/teams", body, {withCredentials: true});
  }

  addTime(name: string, body: AddTimeBody) {
    return this.client.post(environment.apiUrl + '/teams/' + name, body, {withCredentials: true});
  }
}
