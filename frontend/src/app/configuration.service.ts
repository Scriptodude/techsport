import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import moment from 'moment';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ApplicationConfigurationRequest, ApplicationConfigurationResponse } from './models/applicationConfiguration';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor(private client: HttpClient) { }

  getConfiguration() {
    return this.client.get<ApplicationConfigurationResponse>(environment.apiUrl + "/config").pipe(map(this.fixResponse));
  }

  updateConfiguration(request: ApplicationConfigurationRequest) {
    const actualRequest: ApplicationConfigurationRequest = {
      startDate: request.startDate,
      endDate: request.endDate,
      modifiers: [...request.modifiers.entries()].reduce((obj, [key, value]) => (obj[key] = value, obj), {}),
      mode: request.mode
    }
    return this.client.put<ApplicationConfigurationResponse>(environment.apiUrl + "/config", actualRequest, { withCredentials: true }).pipe(map(this.fixResponse));
  }

  private fixResponse(config: ApplicationConfigurationResponse) {
    config.pointModifiers = new Map<string, number>(Object.entries(config.pointModifiers));
    config.supportedActivities = new Map<string, string>(Object.entries(config.supportedActivities));
    config.startDateMtl = moment.utc(config.startDate).tz("America/Montreal")
    config.endDateMtl = moment.utc(config.endDate).tz("America/Montreal")
    return config;
  }
}
