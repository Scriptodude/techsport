import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { RulesComponent } from './rules/rules.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';
import { StravaComponent } from './strava/strava.component';
import { TeamsComponent } from './teams/teams.component';

const routes: Routes = [
  { path: "admin", component: AdminComponent },
  { path: "equipes", component: TeamsComponent },
  { path: "pointage", component: ScoreboardComponent },
  { path: "strava", component: StravaComponent },
  { path: "accueil", component: RulesComponent},
  { path: "", redirectTo: '/accueil', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
