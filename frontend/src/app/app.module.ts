import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigationComponent } from './navigation/navigation.component';
import { FormsModule } from '@angular/forms';
import { TeamsComponent } from './teams/teams.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';
import { StravaComponent } from './strava/strava.component';
import { RulesComponent } from './rules/rules.component';
import { AngularEmojisModule } from 'angular-emojis';
import { AdminModule } from './admin/admin.module';
import { ScoreboardTimeComponent } from './scoreboard-time/scoreboard-time.component';
import { ScoreboardPointsComponent } from './scoreboard-points/scoreboard-points.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    TeamsComponent,
    ScoreboardComponent,
    StravaComponent,
    RulesComponent,
    ScoreboardTimeComponent,
    ScoreboardPointsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    AngularEmojisModule,
    AdminModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
