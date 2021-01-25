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
          y += Math.round(entry[1] / 36) / 100
          const dateData = entry[0].split('-').map(v => parseInt(v, 10))
          dataPoints.push({ x: new Date(dateData[0], dateData[1] - 1, dateData[2]), y: y });
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
        axisY:{
          scaleBreaks: {
            autoCalculate: true
          },
          includeZero: true,
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

    const totalSeconds = Object.entries(team.timeChanges).sort((a, b) => a[0].localeCompare(b[0]))[0][1]
    const hours = Math.floor(totalSeconds / 3600)
    const minutes = Math.floor((totalSeconds - hours * 3600) / 60);
    const seconds = Math.floor((totalSeconds - hours * 3600 - minutes * 60));

    return hours + "h " + minutes + "m " + seconds + "s ";
  }

}
