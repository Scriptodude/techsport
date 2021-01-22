import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private client: HttpClient) { }

  isAdmin() {
    return this.client.post(environment.apiUrl + "/isAdmin", null, { withCredentials: true, observe: 'response' })
  }
}
