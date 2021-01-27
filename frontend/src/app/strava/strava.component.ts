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

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
