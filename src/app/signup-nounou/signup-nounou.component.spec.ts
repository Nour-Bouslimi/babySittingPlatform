import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupNounouComponent } from './signup-nounou.component';

describe('SignupNounouComponent', () => {
  let component: SignupNounouComponent;
  let fixture: ComponentFixture<SignupNounouComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SignupNounouComponent]
    });
    fixture = TestBed.createComponent(SignupNounouComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
