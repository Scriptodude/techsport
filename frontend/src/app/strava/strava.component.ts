import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';

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

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
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
        case "3":
        default:
          this.reason = 'Erreur lors de l\'import des données.'
          break;
      }
  });
  }

}
