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
    const min = Math.max(this.page - 2, 1)
    const result: number[] = []
    let i = min;

    while(result.length != 5 && i <= this.pageCount) {
      result.push(i);
      i++;
    }

    return result;
  }

  changePage(page: number) {
    this.page = page
    this.pageChanged.emit(this.page);
  }

}
