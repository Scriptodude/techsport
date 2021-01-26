import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import Activity from './models/activity';
import ChangeActivityApproval from './models/changeActivityApproval';
import PageCountResponse from './models/pageCountResponse';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  constructor(private client: HttpClient) { }

  getActivitiesToValidate(page: number) {
    return this.client.get<Activity[]>(environment.apiUrl + "/activities/to-validate?page=" + (page - 1), { withCredentials: true})
  }

  getAllActivities(page: number) {
    return this.client.get<Activity[]>(environment.apiUrl + "/activities?page=" + (page - 1), { withCredentials: true })
  }

  changeActivityApproval(changeActivityApproval: ChangeActivityApproval) {
    return this.client.post(environment.apiUrl + "/activities", changeActivityApproval, { withCredentials: true, observe: 'response' })
  }

  getPageCount() {
    return this.client.get<PageCountResponse>(environment.apiUrl + "/activities/pages", { withCredentials: true })
  }
}
