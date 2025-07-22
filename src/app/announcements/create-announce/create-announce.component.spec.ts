import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAnnounceComponent } from './create-announce.component';

describe('CreateAnnounceComponent', () => {
  let component: CreateAnnounceComponent;
  let fixture: ComponentFixture<CreateAnnounceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateAnnounceComponent]
    });
    fixture = TestBed.createComponent(CreateAnnounceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
