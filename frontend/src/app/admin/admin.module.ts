import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateTeamComponent } from '../create-team/create-team.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularEmojisModule } from 'angular-emojis';
import { AppRoutingModule } from '../app-routing.module';
import { AdminComponent } from './admin.component';
import { EditTeamComponent } from '../edit-team/edit-team.component';
import { ActivitiesComponent } from '../activities/activities.component';
import { AddTimeComponent } from '../add-time/add-time.component';
import { TimeLogComponent } from '../time-log/time-log.component';
import { PaginationComponent } from '../pagination/pagination.component';
import { AdminConfigComponent } from '../admin-config/admin-config.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { NgxMatMomentModule, NgxMatMomentAdapter, NGX_MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular-material-components/moment-adapter';
import { NgxMatDatetimePickerModule, NgxMatTimepickerModule, NgxMatDateAdapter } from '@angular-material-components/datetime-picker';
import { ActivityBodyComponent } from '../activity-body/activity-body.component';

@NgModule({
  declarations: [
    AdminComponent,
    CreateTeamComponent,
    EditTeamComponent,
    ActivitiesComponent,
    AddTimeComponent,
    TimeLogComponent,
    PaginationComponent,
    AdminConfigComponent,
    ActivityBodyComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AngularEmojisModule,
    MatDatepickerModule,
    BrowserAnimationsModule,
    MatMomentDateModule,
    MatFormFieldModule,
    NgxMatTimepickerModule,
    NgxMatDatetimePickerModule,
    MatInputModule,
    MatButtonModule,
    NgxMatMomentModule
  ],
  exports: [
    PaginationComponent,
    ActivityBodyComponent
  ],
  providers: [
    { provide: NGX_MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: true, strict: true } },
    { provide: NgxMatDateAdapter, useClass: NgxMatMomentAdapter }]
})
export class AdminModule { }
