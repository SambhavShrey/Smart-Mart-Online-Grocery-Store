import { Component, OnInit } from '@angular/core';
import { PopupServiceService } from 'src/app/serivces/popup-service.service';

@Component({
  selector: 'app-token-expired-popup',
  templateUrl: './token-expired-popup.component.html',
  styleUrls: ['./token-expired-popup.component.css']
})
export class TokenExpiredPopupComponent implements OnInit {

  show = false;

  constructor(private popupService: PopupServiceService) {}

  ngOnInit() {
    this.popupService.popupState$.subscribe(
      state => this.show = state
    );
  }

  closePopup() {
    this.popupService.hidePopup();
  }

}
