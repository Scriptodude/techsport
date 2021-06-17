import { Component, OnInit } from '@angular/core';
import moment, { Moment } from 'moment-timezone';
import { ActivityService } from '../activity.service';
import { ConfigurationService } from '../configuration.service';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';
import Team from '../models/team';
import TranslatedComponent from '../models/translation/translatedComponent';
import { TeamsService } from '../teams.service';
import * as CanvasJS from './canvasjs.min';

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.scss']
})
export class ScoreboardComponent extends TranslatedComponent implements OnInit {

  teams: Team[] = [];
  config: ApplicationConfigurationResponse = createDefaultConfigResponse();
  showBoard = true;
  startDateFormatted = '1er janvier 2021 à 00:00'

  private teamColor = new Map<string, string>();
  pointPerSport = new Map<string, Map<string, number>>();

  constructor(private teamService: TeamsService, private configService: ConfigurationService, private activityService: ActivityService) { super() }

  ngOnInit(): void {
    super.init();
    this.configService.getConfiguration().subscribe(c => {
      this.config = c

      let startDate = moment.tz(moment(this.config.startDate), 'Etc/UTC')
      let now = moment.tz(moment(), 'Etc/UTC')
      let endDate = moment.tz(moment(this.config.endDate), 'Etc/UTC')
      this.startDateFormatted = this.config.startDateMtl.locale("fr").format("LL à HH:mm");

      if (now < startDate) {
        this.showBoard = false;
      } else {
        this.teamService.getAllTeams().subscribe(teams => {
          this.teams = teams

          let data: any = [];
          let maxDate = now;
          let maxValue = 0;
          let dataCount = 0;

          if (now > endDate) {
            maxDate = endDate;
          }

          for (let team of this.teams) {

            if (this.config.appMode == 'distancePerSport') {
              this.activityService.getPointsPerActivityType(team.name).subscribe(pts => {
                this.pointPerSport.set(team.name, new Map<string, number>(Object.entries(pts)));
              })
            }

            let y = 0;
            let dataPoints: any = [];

            for (var m = startDate.clone(); m.isBefore(maxDate); m.add(1, 'days')) {
              y += this.incrementPoints(m, team);
              const date = m.toDate()
              date.setHours(0, 0, 0, 0);
              dataPoints.push({ x: date, y });
              maxValue = Math.max(maxValue, y);
              dataCount++;
            }

            data.push({
              type: 'line',
              color: this.getTeamColor(team.name),
              name: team.name,
              showInLegend: true,
              markerType: "circle",
              markerSize: 10,
              lineThickness: 3,
              dataPoints
            })
          }

          let chart = new CanvasJS.Chart("chartContainer", {
            zoomEnabled: true,
            animationEnabled: true,
            exportEnabled: false,
            title: {
              text: this.isTimeMode() ? "Total du temps cumulé" : (now <= endDate ? this.translation.scoreboard.titleNow : this.translation.scoreboard.titleDone)
            },
            axisX: {
              valueFormatString: "DD/MM",
              interval: 1,
              intervalType: "day"
            },
            axisY: {
              includeZero: false,
              minimum: 0,
              maximum: maxValue * 1.25
            },
            legend: {
              cursor: "pointer",
              verticalAlign: "top",
              horizontalAlign: "center",
              dockInsidePlotArea: false,
            },
            data
          });

          chart.render();
        });
      }
    });
  }

  totalName() {
    if (this.isTimeMode()) {
      return "Temps total"
    } else {
      return "Points total"
    }
  }

  todayName() {
    if (this.isTimeMode()) {
      return "Temps aujourd'hui"
    } else {
      return this.translation.scoreboard.pointsToday;
    }
  }

  teamTotalFormatted(team: Team) {
    if (this.isTimeMode()) {
      return `${team.timeTotal.hours}h ${team.timeTotal.minutes}m ${team.timeTotal.seconds}s`;
    } else {
      return team.pointsTotal.toFixed(2);
    }
  }

  getTeamLastAdd(idx: number) {
    if (idx < 0 || idx > this.teams.length) return "Aucun";

    const team = this.teams[idx];

    if (Object.entries(team.pointChanges).length == 0) return "Aucun";

    if (this.isTimeMode()) {
      const hours = team.timeToday.hours
      const minutes = team.timeToday.minutes
      const seconds = team.timeToday.seconds

      return hours + "h " + minutes + "m " + seconds + "s ";
    } else {
      return team.pointsToday.toFixed(2);
    }
  }

  getTeamStats(idx: number, name: string) {
    if (idx < 0 || idx > this.teams.length) return "N/a";

    const team = this.teams[idx];

    const entries = new Map<string, number>(Object.entries(team.pointChanges))
    if (entries.size == 0) return "N/a";

    const sorted = Array.from(entries.values()).sort((a, b) => a - b)
    const minV = sorted[0]
    const maxV = sorted[sorted.length - 1]
    const avgV = sorted.reduce((acc, v) => acc + v) / sorted.length

    switch (name) {
      case "max": return this.secondsToHms(maxV)
      case "min": return this.secondsToHms(minV)
      default: return this.secondsToHms(avgV)
    }
  }

  getTeamColor(name: string) {
    if (name.toLowerCase().includes('techso')) {
      return 'red';
    } else if (name.toLowerCase().includes('flinks')) {
      return 'blue';
    } else {
      if (this.teamColor.has(name)) {
        return this.teamColor.get(name)
      }

      const color = '#' + Math.floor(Math.random() * 16777215).toString(16);
      this.teamColor.set(name, color);
      return color;
    }
  }

  getPoints(name: string, type: string) {
    if (!this.pointPerSport.has(name)) return 0;

    const ptsTeam = this.pointPerSport.get(name)
    switch (type) {
      case 'walk':
        const walk = ptsTeam?.get('walk') || 0
        const hike = ptsTeam?.get('hike') || 0
        return walk + hike;
      case 'run':
        const run = ptsTeam?.get('run') || 0
        const virtual = ptsTeam?.get('virtualRun') || 0
        return run + virtual;
      case 'ride':
        const ride = ptsTeam?.get('ride') || 0
        const virtualRide = ptsTeam?.get('virtualRide') || 0
        return ride + virtualRide;
      default:
        return ptsTeam?.get('inlineSkate') || 0
    }
  }

  private incrementPoints(moment: moment.Moment, team: Team) {
    let changes = new Map(Object.entries(team.pointChanges))
    const dateFormatted = moment.format("YYYY-MM-DD");
    let y = 0;

    if (changes.has(dateFormatted)) {
      y += changes.get(dateFormatted)
    }

    return this.isTimeMode() ? parseFloat((y / 3600).toFixed(2)) : y;
  }

  private secondsToHms(d: number) {
    if (this.isTimeMode()) {
      d = Number(d);
      var h = Math.floor(d / 3600);
      var m = Math.floor(d % 3600 / 60);
      var s = Math.floor(d % 3600 % 60);

      var hDisplay = h > 0 ? h + "h " : "";
      var mDisplay = m > 0 ? m + "m " : "";
      var sDisplay = s > 0 ? s + "s" : "";
      return hDisplay + mDisplay + sDisplay;
    } else {
      return d.toFixed(2);
    }
  }

  private isTimeMode() {
    return this.config.appMode === 'time';
  }
}
