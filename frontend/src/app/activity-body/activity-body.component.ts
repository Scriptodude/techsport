import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ConfigurationService } from '../configuration.service';
import { Activity, createDefaultActivity } from '../models/activity';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';
import TranslatedComponent from '../models/translation/translatedComponent';

@Component({
  selector: 'app-activity-body',
  templateUrl: './activity-body.component.html',
  styleUrls: ['./activity-body.component.scss']
})
export class ActivityBodyComponent extends TranslatedComponent implements OnInit {

  @Input() activity: Activity = createDefaultActivity()
  @Output() changeApprovalEmiter = new EventEmitter<any[]>()
  @Input() isTimeMode = true
  @Input() supportedActivities = new Map<String, String>()

  constructor() { super() }

  ngOnInit(): void {
    super.init();
  }

  showMaths() {
    return `${this.activity.distance} x ${this.activity.appliedRate || 1.0}`
  }

  getNamedType() {
    if (this.activity.type == null || !this.supportedActivities.has(this.activity.type)) {
      return this.translation.activityCard.unsupported;
    }

    return this.supportedActivities.get(this.activity.type);
  }

  getStatus() {
    const teamName = this.activity.teamName || 'Inconnue';
    console.log(teamName);
    if (this.activity == null) return ''
    if (this.activity.approved === true) return 'Status: ' + this.translation.activityCard.approved + ' ' + teamName
    else if (this.activity.approved === false) return 'Status: ' + this.translation.activityCard.declined
    return 'Status: ' + this.translation.activityCard.toValidate
  }
}
