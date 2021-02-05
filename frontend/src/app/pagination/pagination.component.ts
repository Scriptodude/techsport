import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  @Input() page: number = 1;
  @Input() pageCount: number = 1;
  @Output() pageChanged = new EventEmitter<number>()

  constructor() { }

  ngOnInit(): void {
  }

  pages() {
    return Array(this.pageCount)
  }

  changePage(page: number) {
    this.page = page
    this.pageChanged.emit(this.page);
  }

}
