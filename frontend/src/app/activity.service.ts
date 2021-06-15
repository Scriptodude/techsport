import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import ActivityResponse from './models/activity';
import Activity from './models/activity';
import ChangeActivityApproval from './models/changeActivityApproval';
import PageCountResponse from './models/pageCountResponse';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  constructor(private client: HttpClient) { }

  getAllActivities(page: number, approved: boolean | null = null, all = false, team: string | null = null) {
    let queryParam = "?page=" + (page - 1);
    queryParam += approved == null ? '' : '&approved=' + approved;
    queryParam += team == null ? '' : '&team=' + team;
    queryParam += '&all=' + all;

    return this.client.get<ActivityResponse>(environment.apiUrl + "/activities" + queryParam, { withCredentials: true })
  }

  changeActivityApproval(changeActivityApproval: ChangeActivityApproval) {
    return this.client.post(environment.apiUrl + "/activities", changeActivityApproval, { withCredentials: true, observe: 'response' })
  }

  getPointsPerActivityType(team: string): Observable<any> {
    return this.client.get<any>(environment.apiUrl + "/activities/points?team=" + team);
  }
}
