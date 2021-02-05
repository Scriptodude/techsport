import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TimeLogResponse } from './models/time-log';

@Injectable({
  providedIn: 'root'
})
export class TimeLogService {

  constructor(private client: HttpClient) { }

  getAllLogs(page: number) {
    return this.client.get<TimeLogResponse>(environment.apiUrl + "/logs?page=" + (page - 1), { withCredentials: true });
  }
}
