import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PopupServiceService {

  private popupSubject = new Subject<boolean>();
  popupState$ = this.popupSubject.asObservable();

  showPopup() {
    this.popupSubject.next(true);
  }

  hidePopup() {
    this.popupSubject.next(false);
  }
}
