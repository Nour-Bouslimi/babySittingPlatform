import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisponibilitiesComponent } from './disponibilities.component';

describe('DisponibilitiesComponent', () => {
  let component: DisponibilitiesComponent;
  let fixture: ComponentFixture<DisponibilitiesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DisponibilitiesComponent]
    });
    fixture = TestBed.createComponent(DisponibilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
