import { Component, DoCheck, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit,DoCheck{
  userName: string;
  profilePic: string;
  showProfileSelector: boolean = false;
  availablePics: string[] = [
    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?t=st=1746006447~exp=1746010047~hmac=f75fae8f0c0bd5bc3304db4c3422b1f13dffcfb4b0bebbfdfbb357dea3891777&w=900',
    'https://img.freepik.com/premium-vector/people-profile-icon_24877-40760.jpg?ga=GA1.1.1568756912.1745817163&semt=ais_hybrid&w=740',
    'https://img.freepik.com/free-vector/man-profile-account-picture_24908-81754.jpg?ga=GA1.1.1568756912.1745817163&semt=ais_hybrid&w=740',
    'https://img.freepik.com/free-vector/purple-man-with-blue-hair_24877-82003.jpg?t=st=1746006822~exp=1746010422~hmac=b3f1ea27746231961e00aa59d41f41780c3b13ce176a0a7ffd103f668dcd4265&w=900',
    'https://img.freepik.com/free-photo/cartoon-man-wearing-glasses_23-2151136781.jpg?ga=GA1.1.1568756912.1745817163&semt=ais_hybrid&w=740',
    'https://img.freepik.com/free-photo/anime-eyes-illustration_23-2151660486.jpg?ga=GA1.1.1568756912.1745817163&semt=ais_hybrid&w=740'
  ];

  constructor(public readonly authService: AuthService, private readonly router: Router) {}

  ngOnInit(): void {
    this.userName = localStorage.getItem('username') || 'User';
    this.profilePic = localStorage.getItem('profilePic') || this.availablePics[0];
  }
  ngDoCheck(): void {
    this.userName = localStorage.getItem('username') || 'User';
    this.profilePic = localStorage.getItem('profilePic') || this.availablePics[0];
}

  logout(): void {
    // Call your logout logic here, then navigate.
    this.router.navigate(['/logout']);
  }
  
  openProfileSelector(): void {
    this.showProfileSelector = true;
  }
  
  closeProfileSelector(): void {
    this.showProfileSelector = false;
  }
  
  selectProfilePic(pic: string): void {
    this.profilePic = pic;
    localStorage.setItem('profilePic', pic);
    this.closeProfileSelector();
  }
  
  // Keyboard event handler for both the div and img.
  handleProfileKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
      this.openProfileSelector();
    }
  }
}
