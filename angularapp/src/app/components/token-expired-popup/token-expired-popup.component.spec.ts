import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenExpiredPopupComponent } from './token-expired-popup.component';

describe('TokenExpiredPopupComponent', () => {
  let component: TokenExpiredPopupComponent;
  let fixture: ComponentFixture<TokenExpiredPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TokenExpiredPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenExpiredPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
