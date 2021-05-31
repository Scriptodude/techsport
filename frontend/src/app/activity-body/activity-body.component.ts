import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ConfigurationService } from '../configuration.service';
import { Activity, createDefaultActivity } from '../models/activity';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';

@Component({
  selector: 'app-activity-body',
  templateUrl: './activity-body.component.html',
  styleUrls: ['./activity-body.component.scss']
})
export class ActivityBodyComponent implements OnInit {

  @Input() activity: Activity = createDefaultActivity()
  @Output() changeApprovalEmiter = new EventEmitter<any[]>()
  @Input() isTimeMode = true
  @Input() supportedActivities = new Map<String, String>()

  constructor() { }

  ngOnInit(): void {
  }
  showMaths() {
    return `${this.activity.distance} x ${this.activity.appliedRate || 1.0}`
  }

  getNamedType() {
    if (this.activity.type == null || !this.supportedActivities.has(this.activity.type)) {
      return 'Type non supporté';
    }

    return this.supportedActivities.get(this.activity.type);
  }

  getStatus() {
    if (this.activity == null) return ''
    if (this.activity.approved === true) return 'Status: Approuvée pour l\'équipe ' + this.activity.teamName || 'Inconnue';
    else if (this.activity.approved === false) return 'Status: Refusée'
    return 'Status: À Valider'
  }
}
