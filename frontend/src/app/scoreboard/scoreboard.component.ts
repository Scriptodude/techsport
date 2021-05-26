import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../configuration.service';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.scss']
})
export class ScoreboardComponent implements OnInit {

  config: ApplicationConfigurationResponse = createDefaultConfigResponse();

  constructor(private configService: ConfigurationService) { }

  ngOnInit(): void {
      this.configService.getConfiguration().subscribe(c => this.config = c);
  }

  isTimeMode() {
    return this.config.appMode === 'time';
  }
}
