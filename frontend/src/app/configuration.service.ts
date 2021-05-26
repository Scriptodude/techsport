import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApplicationConfigurationRequest, ApplicationConfigurationResponse } from './models/applicationConfiguration';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor(private client: HttpClient) { }

  getConfiguration() {
    return this.client.get<ApplicationConfigurationResponse>(environment.apiUrl + "/config");
  }

  updateConfiguration(request: ApplicationConfigurationRequest) {
    return this.client.put<ApplicationConfigurationResponse>(environment.apiUrl + "/config", request, {withCredentials: true});
  }
}
