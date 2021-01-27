import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigationComponent } from './navigation/navigation.component';
import { AdminComponent } from './admin/admin.component';
import { FormsModule } from '@angular/forms';
import { TeamsComponent } from './teams/teams.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';
import { StravaComponent } from './strava/strava.component';
import { RulesComponent } from './rules/rules.component';
import { AngularEmojisModule } from 'angular-emojis';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    AdminComponent,
    TeamsComponent,
    ScoreboardComponent,
    StravaComponent,
    RulesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    AngularEmojisModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
