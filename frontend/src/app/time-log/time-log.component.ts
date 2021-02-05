import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import ComponentMessage from '../models/componentMessage';
import { TimeLog } from '../models/time-log';
import { TimeLogService } from '../time-log.service';

@Component({
  selector: 'app-time-log',
  templateUrl: './time-log.component.html',
  styleUrls: ['./time-log.component.scss']
})
export class TimeLogComponent implements OnInit {

  pageCount: number = 1;
  logs: TimeLog[] = [];
  @Output() message = new EventEmitter<ComponentMessage>()

  constructor(private timeLogService: TimeLogService) { }

  ngOnInit(): void {
    this.changePage(1);
  }

  changePage(page: number) {
    this.timeLogService.getAllLogs(page)
      .pipe(catchError(this.handleError.bind(this)))
      .subscribe(r => {
        if (r == null) return;
        this.logs = r.logs;
        this.pageCount = r.pages;
      })
  }

  private handleError(error: HttpErrorResponse) {
    switch (error.status) {
      case 401:
        this.message.emit(new ComponentMessage(false, 'Vous ne pouvez pas voir le contenu de cette page.'));
        break;
      default: this.message.emit(new ComponentMessage(false, 'Erreur lors du chargement du journal des heures.'));
    }

    throwError("Could not finish the call")
    return of(null)
  }
}
