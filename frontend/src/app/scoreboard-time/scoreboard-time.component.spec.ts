import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreboardTimeComponent } from './scoreboard-time.component';

describe('ScoreboardTimeComponent', () => {
  let component: ScoreboardTimeComponent;
  let fixture: ComponentFixture<ScoreboardTimeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScoreboardTimeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScoreboardTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
