import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import moment from 'moment-timezone';
import { config, of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ConfigurationService } from '../configuration.service';
import { ApplicationConfigurationRequest, ApplicationConfigurationResponse, createDefaultConfigRequest, createDefaultConfigResponse } from '../models/applicationConfiguration';
import ComponentMessage from '../models/componentMessage';

@Component({
  selector: 'app-admin-config',
  templateUrl: './admin-config.component.html',
  styleUrls: ['./admin-config.component.scss']
})
export class AdminConfigComponent implements OnInit {

  @Output() message = new EventEmitter<ComponentMessage>();
  @Output() configUpdated = new EventEmitter<ApplicationConfigurationResponse>();
  @Input() config: ApplicationConfigurationResponse = createDefaultConfigResponse();
  configRequest: ApplicationConfigurationRequest = createDefaultConfigRequest();
  start = new FormControl()
  end = new FormControl()
  modifiers = new FormGroup({});

  constructor(private configService: ConfigurationService) { }

  ngOnInit(): void {
    this.configRequest.mode = this.config.appMode;
    this.configRequest.modifiers = this.config.pointModifiers;
    this.configRequest.startDate = this.config.startDate;
    this.configRequest.endDate = this.config.endDate;
    this.start.setValue(this.config.startDateMtl);
    this.end.setValue(this.config.endDateMtl);
    this.getAvailableSports().map(a => a[0]).forEach(v => this.modifiers.addControl(v, new FormControl(this.configRequest.modifiers.get(v) || 1.0)));
  }

  updateConfig() {
    if (this.start.value === null) {
      this.message.emit(new ComponentMessage(false, "La date de début est requise."));
      return;
    }

    if (this.end.value === null) {
      this.message.emit(new ComponentMessage(false, "la date de fin est requise."));
      return;
    }

    this.configRequest.startDate = moment.utc(this.start.value).tz("America/Montreal").utc().format()
    this.configRequest.endDate = moment.utc(this.end.value).tz("America/Montreal").utc().format()
    

    if (this.configRequest.mode === 'distancePerSport') {
      this.getAvailableSports().map(a => a[0]).forEach(v => this.configRequest.modifiers.set(v, this.modifiers.get(v)?.value));
    }

    this.configService.updateConfiguration(this.configRequest)
    .pipe(catchError(this.handleError.bind(this)))
    .subscribe(r => {
      if (r == null) return;
      this.configUpdated.emit(r)
      this.message.emit(new ComponentMessage(true, "Configuration mise à jour avec succès"));
    });
  }

  getSelectedMode() {
    if (this.configRequest.mode === 'time') {
      return "Pointage par temps";
    } else {
      return "Pointage par distance par activité"
    }
  }

  getAvailableModes() {
    return [["time", "Pointage par temps"], ["distancePerSport", "Pointage par distance par activité"]]
  }

  getAvailableSports() {
    return Array.from(this.config.supportedActivities.entries());
  }

  selectMode(mode: string) {
    this.configRequest.mode = mode;
  }

  trackByFn(index, item) {
    return index;
  }

  private handleError(error: HttpErrorResponse) {
    this.message.emit(new ComponentMessage(false, 'Erreur lors de la mise à jour de la configuration.'));

    throwError("Could not finish the call");
    return of(null)
  }
}
