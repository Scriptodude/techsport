import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreboardPointsComponent } from './scoreboard-points.component';

describe('ScoreboardPointsComponent', () => {
  let component: ScoreboardPointsComponent;
  let fixture: ComponentFixture<ScoreboardPointsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScoreboardPointsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScoreboardPointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
