import { Component, OnInit } from '@angular/core';
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

  constructor(private teamService: TeamsService) { }

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe(teams => {
      this.teams = teams

      let data: any = [];
      for (let team of this.teams) {
        let y = 0;
        let dataPoints: any = [];

        for (let entry of new Map(Object.entries(team.timeChanges))) {
          y += entry[1]
          const dateData = entry[0].split('-').map(v => parseInt(v, 10))
          dataPoints.push({ x: new Date(dateData[0], dateData[1] - 1, dateData[2]), y: parseFloat((y / 3600).toFixed(2)) });
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
          text: "Total du temps cumulé"
        },
        axisX: {
          valueFormatString: "DD/MM",
          interval: 1,
          intervalType: "day"
        },
        axisY: {
          scaleBreaks: {
            autoCalculate: true
          },
          includeZero: false,
        },
        legend: {
          cursor: "pointer",
          verticalAlign: "top",
          horizontalAlign: "center",
          dockInsidePlotArea: true,
        },
        data
      });

      chart.render();
    });
  }

  getTeamLastAdd(idx: number) {
    if (idx < 0 || idx > this.teams.length) return "Aucun";

    const team = this.teams[idx];

    if (Object.entries(team.timeChanges).length == 0) return "Aucun";

    const hours = team.timeToday.hours
    const minutes = team.timeToday.minutes
    const seconds = team.timeToday.seconds

    return hours + "h " + minutes + "m " + seconds + "s ";
  }

  getTeamStats(idx: number, name: string) {
    if (idx < 0 || idx > this.teams.length) return "N/a";

    const team = this.teams[idx];

    const entries = new Map<string, number>(Object.entries(team.timeChanges))
    if (entries.size == 0) return "N/a";

    const sorted = Array.from(entries.values()).sort()
    const minV = sorted[0]
    const maxV = sorted[sorted.length - 1]
    const avgV = sorted.reduce((acc, v) => acc + v) / sorted.length

    switch (name) {
      case "max": return this.secondsToHms(maxV)
      case "min": return this.secondsToHms(minV)
      default: return this.secondsToHms(avgV.toFixed(2))
    }
  }

  private secondsToHms(d) {
    d = Number(d);
    var h = Math.floor(d / 3600);
    var m = Math.floor(d % 3600 / 60);
    var s = Math.floor(d % 3600 % 60);

    var hDisplay = h > 0 ? h + "h " : "";
    var mDisplay = m > 0 ? m + "m " : "";
    var sDisplay = s > 0 ? s + "s" : "";
    return hDisplay + mDisplay + sDisplay;
  }

}
