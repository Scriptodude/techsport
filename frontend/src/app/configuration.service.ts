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
    const actualRequest: ApplicationConfigurationRequest = {
      startDate: request.startDate,
      endDate: request.endDate,
      modifiers: [...request.modifiers.entries()].reduce((obj, [key, value]) => (obj[key] = value, obj), {}),
      mode: request.mode
    }
    return this.client.put<ApplicationConfigurationResponse>(environment.apiUrl + "/config", actualRequest, {withCredentials: true});
  }
}
