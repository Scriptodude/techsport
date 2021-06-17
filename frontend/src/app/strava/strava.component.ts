import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import moment, { locale } from 'moment-timezone';
import { environment } from 'src/environments/environment';
import { ActivityService } from '../activity.service';
import { ConfigurationService } from '../configuration.service';
import { Activity } from '../models/activity';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';

@Component({
  selector: 'app-strava',
  templateUrl: './strava.component.html',
  styleUrls: ['./strava.component.scss']
})
export class StravaComponent implements OnInit {

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

  constructor(private route: ActivatedRoute, private configService: ConfigurationService, private activityService: ActivityService) { }

  ngOnInit(): void {
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
      this.startDate = startDate.locale("fr").format("LL à HH:mm")
      this.endDate = endDate.locale("fr").format("LL à HH:mm")

      this.isStarted = (now >= startDate && now <= endDate);
    })

    this.route.queryParams.subscribe(params => {
      this.success = params["success"];
      this.failure = params["failure"];
      const reason = params["reason"];
      this.count = params["count"] || 0;

      switch (reason) {
        case "1":
          this.reason = 'Vous ne faites pas parti du club techso.'
          break;
        case "2":
          this.reason = 'Vous pouvez importer vos données qu\'aux 15 minutes.'
          break;
        case "4":
          this.reason = 'L\'import est fermé, l\'événement terminait le ' + this.endDate + ", heures de montréal"
          break;
        case "3":
        default:
          this.reason = 'Erreur lors de l\'import des données.'
          break;
      }
  });
  }

  changePage(page: number) {
    this.page = page
    this.getActivities()
  }

  private getActivities() {
    this.activityService.getActivitiesForUser(this.page).subscribe(a => { this.activities = a.activities; this.pageCount = a.pages });
  }

}
