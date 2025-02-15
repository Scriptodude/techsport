import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss']
})
export class RulesComponent implements OnInit {

  isEnglish = false;

  constructor() { }

  ngOnInit(): void {
    this.isEnglish = sessionStorage.getItem('locale') === 'en';
  }

}
