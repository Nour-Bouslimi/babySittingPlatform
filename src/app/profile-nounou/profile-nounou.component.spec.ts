import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileNounouComponent } from './profile-nounou.component';

describe('ProfileNounouComponent', () => {
  let component: ProfileNounouComponent;
  let fixture: ComponentFixture<ProfileNounouComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileNounouComponent]
    });
    fixture = TestBed.createComponent(ProfileNounouComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
