import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import moment from 'moment-timezone';
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
    this.configRequest.modifiers = new Map<string, number>(Object.entries(this.config.pointModifiers));
    this.configRequest.startDate = this.config.startDate;
    this.configRequest.endDate = this.config.endDate;
    this.start.setValue(moment.utc(this.config.startDate).tz("America/Montreal"));
    this.end.setValue(moment.utc(this.config.endDate).tz("America/Montreal"));
    this.getAvailableSports().map(a => a[0]).forEach(v => this.modifiers.addControl(v, new FormControl(this.configRequest.modifiers.get(v) || 1.0)));
  }

  updateConfig() {
    this.configRequest.startDate = moment.utc(this.start.value).tz("America/Montreal", true).utc(true).format()
    this.configRequest.endDate = moment.utc(this.end.value).tz("America/Montreal").utc().format()

    if (this.configRequest.mode === 'distancePerSport') {
      this.getAvailableSports().map(a => a[0]).forEach(v => this.configRequest.modifiers.set(v, this.modifiers.get(v)?.value));
    }

    console.log(this.configRequest);
    this.configService.updateConfiguration(this.configRequest).subscribe(r => this.configUpdated.next(r));
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
    return [["Walk", "Marche à pied"], ["Run", "Course à pied extérieure"], ["VirtualRun", "Course à pied sur tapis roulant"], ["Ride", "Vélo"], ["VirtualRide", "Vélo Stationnaire"], ["InlineSkate", "Patin à roues alignées"]]
  }

  selectMode(mode: string) {
    this.configRequest.mode = mode;
  }

  trackByFn(index, item) {
    return index;
  }
}
