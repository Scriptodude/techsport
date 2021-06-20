import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import moment, { locale } from 'moment-timezone';
import { environment } from 'src/environments/environment';
import { ActivityService } from '../activity.service';
import { ConfigurationService } from '../configuration.service';
import { Activity } from '../models/activity';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';
import TranslatedComponent from '../models/translation/translatedComponent';

@Component({
  selector: 'app-strava',
  templateUrl: './strava.component.html',
  styleUrls: ['./strava.component.scss']
})
export class StravaComponent extends TranslatedComponent implements OnInit {

  apiUrl = environment.apiUrl;

  success = null;
  failure = null;
  reason: String = 'Impossible d\'importer les données'
  count = 0;
  startDate = '2021-01-01 à 00:00:00'
  endDate = '2021-01-01 à 00:00:00'
  isStarted = false;
  hasSession = false;
  config: ApplicationConfigurationResponse = createDefaultConfigResponse()
  page = 1
  pageCount = 1
  activities: Activity[] = [];

  constructor(private route: ActivatedRoute, private configService: ConfigurationService, private activityService: ActivityService) { super() }

  ngOnInit(): void {
    super.init();
    this.activityService.isStravaAvailable().subscribe(r => {
      this.hasSession = r.isAvailable

      if (this.hasSession) {
        this.getActivities();
      }
    });

    this.configService.getConfiguration().subscribe(r => {
      this.config = r;
      const startDate = r.startDateMtl;
      const endDate = r.endDateMtl;
      const now = moment.tz(moment(), 'Etc/UTC')
      this.startDate = startDate.locale(sessionStorage.getItem('locale') || 'fr').format(`LL ${this.translation.timeSeparator} HH:mm`)
      this.endDate = endDate.locale(sessionStorage.getItem('locale') || 'fr').format(`LL ${this.translation.timeSeparator} HH:mm`)

      this.isStarted = (now >= startDate && now <= endDate);
    })

    this.route.queryParams.subscribe(params => {
      this.success = params["success"];
      this.failure = params["failure"];
      const reason = params["reason"];
      this.count = params["count"] || 0;

      switch (reason) {
        case "1":
          this.reason = this.translation.strava.notInTeam
          break;
        case "2":
          this.reason = this.translation.strava.throttle
          break;
        case "4":
          this.reason = this.translation.strava.importClosed
          break;
        case "3":
        default:
          this.reason = this.translation.strava.genericError
          break;
      }
  });
  }

  changePage(page: number) {
    this.page = page
    this.getActivities()
  }

  printOpening() {
    return this.translation.strava.openingFormat
  }

  private getActivities() {
    this.activityService.getActivitiesForUser(this.page).subscribe(a => { this.activities = a.activities; this.pageCount = a.pages });
  }

}
