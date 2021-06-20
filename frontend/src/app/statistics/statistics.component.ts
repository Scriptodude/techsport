import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../configuration.service';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';
import { TeamStatistics } from '../models/statistics';
import TranslatedComponent from '../models/translation/translatedComponent';
import { TeamsService } from '../teams.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent extends TranslatedComponent implements OnInit {

  teamStats: TeamStatistics[] = []
  private config: ApplicationConfigurationResponse = createDefaultConfigResponse()

  constructor(private teamService: TeamsService, private configService: ConfigurationService) { super() }

  ngOnInit(): void {
    super.init()
    this.teamService.getStatsOfTeams().subscribe(r => this.teamStats = r);
    this.configService.getConfiguration().subscribe(r => this.config = r);
  }

  getAvailableSports() {
    return Array.from(this.config.supportedActivities.entries());
  }

  getStatOfSport(stat: TeamStatistics, sport: string, commonStat: string, what: string) {
    if (stat !== null && sport !== null && commonStat !== null && what !== null) {
      if (stat.statsPerType.has(sport)) {
        const statOfSport = stat.statsPerType.get(sport) || new Map() 

        if (statOfSport[commonStat]) {

          if (commonStat === 'time') {
            const d = statOfSport[commonStat][what]
            const h = Math.floor(d / 3600);
            const m = Math.floor(d % 3600 / 60);
            const s = Math.floor(d % 3600 % 60);

            const hDisplay = h > 0 ? h + "h " : "";
            const mDisplay = m > 0 ? m + "m" : "";
            return hDisplay + mDisplay
          } else {
            return statOfSport[commonStat][what]
          }
        }
      }
    }

    return 0
  }

  getGlobalStatOfSport(stat: TeamStatistics, sport: string, what: string) {
    if (stat !== null && sport !== null && what !== null) {
      if (stat.statsPerType.has(sport)) {
        const statOfSport = stat.statsPerType.get(sport) || new Map() 

        if (statOfSport[what]) {
          return statOfSport[what]
        }
      }
    }

    return 0;
  }
}
