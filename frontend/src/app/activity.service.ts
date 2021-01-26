import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import Activity from './models/activity';
import ChangeActivityApproval from './models/changeActivityApproval';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  constructor(private client: HttpClient) { }

  getActivitiesToValidate() {
    return this.client.get<Activity[]>(environment.apiUrl + "/activities/to-validate", { withCredentials: true})
  }

  getAllActivities() {
    return this.client.get<Activity[]>(environment.apiUrl + "/activities", { withCredentials: true })
  }

  changeActivityApproval(changeActivityApproval: ChangeActivityApproval) {
    return this.client.post(environment.apiUrl + "/activities", changeActivityApproval, { withCredentials: true, observe: 'response' })
  }
}
