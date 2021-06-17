import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import TranslatedComponent from '../models/translation/translatedComponent';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent extends TranslatedComponent implements OnInit {

  active = 1;
  isEnglish = false;

  constructor(public route: ActivatedRoute) { 
    super();
  }

  ngOnInit(): void {
    super.init();
    this.isEnglish = sessionStorage.getItem('locale') === 'en';
  }

  changeLang(local: string) {
    sessionStorage.setItem('locale', local)
    location.reload();
  }
}
