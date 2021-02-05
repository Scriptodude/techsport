import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateTeamComponent } from '../create-team/create-team.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularEmojisModule } from 'angular-emojis';
import { AppRoutingModule } from '../app-routing.module';
import { AdminComponent } from './admin.component';
import { EditTeamComponent } from '../edit-team/edit-team.component';
import { ActivitiesComponent } from '../activities/activities.component';
import { AddTimeComponent } from '../add-time/add-time.component';
import { TimeLogComponent } from '../time-log/time-log.component';
import { PaginationComponent } from '../pagination/pagination.component';



@NgModule({
  declarations: [
    AdminComponent,
    CreateTeamComponent,
    EditTeamComponent,
    ActivitiesComponent,
    AddTimeComponent,
    TimeLogComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    AngularEmojisModule
  ]
})
export class AdminModule { }
