import { Component, OnInit } from '@angular/core';
import moment, { Moment } from 'moment-timezone';
import { ConfigurationService } from '../configuration.service';
import { ApplicationConfigurationResponse, createDefaultConfigResponse } from '../models/applicationConfiguration';
import Team from '../models/team';
import { TeamsService } from '../teams.service';
import * as CanvasJS from './canvasjs.min';

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.scss']
})
export class ScoreboardComponent implements OnInit {

  teams: Team[] = [];
  config: ApplicationConfigurationResponse = createDefaultConfigResponse();

  constructor(private teamService: TeamsService, private configService: ConfigurationService) { }

  ngOnInit(): void {
    this.configService.getConfiguration().subscribe(c => this.config = c);

    this.teamService.getAllTeams().subscribe(teams => {
      this.teams = teams

      let data: any = [];
      let startDate = moment.tz(moment(this.config.startDate), 'Etc/UTC')
      let now = moment.tz(moment(), 'Etc/UTC')
      let endDate = moment.tz(moment(this.config.endDate), 'Etc/UTC')
      let maxDate = now; 
      let maxValue = 0;
      let dataCount = 0;

      if (now > endDate) {
        maxDate = endDate;
      }

      for (let team of this.teams) {
        let y = 0;
        let dataPoints: any = [];

        for (var m = startDate.clone(); m.isBefore(maxDate); m.add(1, 'days')) {
          y += this.incrementPoints(m, team);
          dataPoints.push({ x: m.toDate(), y });
          maxValue = Math.max(maxValue, y);
          dataCount++;
        }

        data.push({
          type: 'line',
          name: team.name,
          showInLegend: true,
          markerType: "circle",
          markerSize: 5,
          lineThickness: 3,
          dataPoints
        })
      }

      let chart = new CanvasJS.Chart("chartContainer", {
        zoomEnabled: true,
        animationEnabled: true,
        exportEnabled: false,
        title: {
          text: this.isTimeMode() ? "Total du temps cumulé" : "Pointage Actuel"
        },
        axisX: {
          valueFormatString: "DD/MM",
          interval: 1,
          intervalType: "day"
        },
        axisY: {
          includeZero: false,
          minimum: 0,
          maximum: maxValue * 1.25,
          interval: Math.ceil(maxValue * 1.25 / dataCount)
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
      return "Points aujourd'hui"
    }
  }

  teamTotalFormatted(team: Team) {
    if (this.isTimeMode()) {
      return `${team.timeTotal.hours}h ${team.timeTotal.minutes}m ${team.timeTotal.seconds}s`; 
    } else {
      return team.pointsTotal;
    }
  }

  getTeamLastAdd(idx: number) {
    if (idx < 0 || idx > this.teams.length) return "Aucun";

    const team = this.teams[idx];

    if (Object.entries(team.pointChanges).length == 0) return "Aucun";

    if (!this.isTimeMode()) {
      return team.pointsToday;
    }

    const hours = team.timeToday.hours
    const minutes = team.timeToday.minutes
    const seconds = team.timeToday.seconds

    return hours + "h " + minutes + "m " + seconds + "s ";
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
      default: return this.secondsToHms(avgV.toFixed(2))
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

  private secondsToHms(d) {
    if (!this.isTimeMode()) return d;
    d = Number(d);
    var h = Math.floor(d / 3600);
    var m = Math.floor(d % 3600 / 60);
    var s = Math.floor(d % 3600 % 60);

    var hDisplay = h > 0 ? h + "h " : "";
    var mDisplay = m > 0 ? m + "m " : "";
    var sDisplay = s > 0 ? s + "s" : "";
    return hDisplay + mDisplay + sDisplay;
  }

  private isTimeMode() {
    return this.config.appMode === 'time';
  }
}
