import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
 
@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {
 
  cart: any[];
  displayPopup: boolean = false; // controls the popup visibility
 
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }
 
  ngOnInit(): void {
    // Display the custom confirmation popup
    this.displayPopup = true;
  }
 
  onConfirm(): void {
    // Logout the user and navigate home when confirmed
    this.authService.loggedOut();
    this.displayPopup = false;
    this.router.navigate(['/']);
  }
 
  onCancel(): void {
    // Simply hide popup and navigate home if cancelled
    this.displayPopup = false;
    this.router.navigate(['/']);
  }
}