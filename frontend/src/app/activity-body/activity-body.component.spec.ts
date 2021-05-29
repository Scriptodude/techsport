import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityBodyComponent } from './activity-body.component';

describe('ActivityBodyComponent', () => {
  let component: ActivityBodyComponent;
  let fixture: ComponentFixture<ActivityBodyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivityBodyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
