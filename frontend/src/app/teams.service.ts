import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import AddTimeBody from './models/addTimeBody';
import CreateTeamBody from './models/createTeamBody';
import { ActivityTypeStatistics, TeamStatistics } from './models/statistics';
import Team from './models/team';
import UpdateTeamMembers from './models/updateTeamMembers';

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

  updateTeam(name: string, body: UpdateTeamMembers) {
    return this.client.put(environment.apiUrl + '/teams/' + name, body, {withCredentials: true});
  }

  getStatsOfTeams(): Observable<TeamStatistics[]> {
    return this.client.get<TeamStatistics[]>(environment.apiUrl + '/teams/stats').pipe(map(r => {
      return r.map(s => {
        s.statsPerType = new Map<string, ActivityTypeStatistics>(Object.entries(s.statsPerType));
        return s;
      })
    }))
  }
}
