import { Component, Input, OnInit } from '@angular/core';
import moment from 'moment-timezone';
import Team from '../models/team';
import { TeamsService } from '../teams.service';
import * as CanvasJS from '../scoreboard/canvasjs.min';

@Component({
  selector: 'app-scoreboard-points',
  templateUrl: './scoreboard-points.component.html',
  styleUrls: ['./scoreboard-points.component.scss']
})
export class ScoreboardPointsComponent implements OnInit {

  teams: Team[] = [];
  @Input() startDate = '2021-01-01';
  @Input() endDate = '2021-01-01';

  constructor(private teamService: TeamsService) { }

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe(teams => {
      this.teams = teams

      let data: any = [];
      let startDate = moment.tz(moment(this.startDate), 'Etc/UTC')
      let now = moment.tz(moment(), 'Etc/UTC')
      let endDate = moment.tz(moment(this.endDate), 'Etc/UTC')
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
          const timeChanges = new Map(Object.entries(team.pointChanges))
          const dateFormatted = m.format("YYYY-MM-DD");

          if (timeChanges.has(dateFormatted)) {
            y += timeChanges.get(dateFormatted)
          }

          maxValue = Math.max(maxValue, y);
          dataCount++;
          dataPoints.push({ x: m.toDate(), y });
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
          text: "Pointage Actuel"
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

    return team.timeToday.timeInSeconds;
  }

  getTeamStats(idx: number, name: string) {
    if (idx < 0 || idx > this.teams.length) return "N/a";

    const team = this.teams[idx];

    const entries = new Map<string, number>(Object.entries(team.timeChanges))
    if (entries.size == 0) return "N/a";

    const sorted = Array.from(entries.values()).sort((a, b) => a - b)
    const minV = sorted[0]
    const maxV = sorted[sorted.length - 1]
    const avgV = sorted.reduce((acc, v) => acc + v) / sorted.length

    switch (name) {
      case "max": return maxV;
      case "min": return minV;
      default: return avgV.toFixed(2);
    }
  }
}
