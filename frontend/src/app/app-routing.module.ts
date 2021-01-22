import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AppComponent } from './app.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';
import { TeamsComponent } from './teams/teams.component';

const routes: Routes = [
  { path: "admin", component: AdminComponent },
  { path: "equipes", component: TeamsComponent },
  { path: "pointage", component: ScoreboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
