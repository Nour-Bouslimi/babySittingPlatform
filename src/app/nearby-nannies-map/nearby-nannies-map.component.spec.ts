import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NearbyNanniesMapComponent } from './nearby-nannies-map.component';

describe('NearbyNanniesMapComponent', () => {
  let component: NearbyNanniesMapComponent;
  let fixture: ComponentFixture<NearbyNanniesMapComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NearbyNanniesMapComponent]
    });
    fixture = TestBed.createComponent(NearbyNanniesMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
