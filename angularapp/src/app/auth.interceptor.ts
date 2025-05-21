import { Injectable, NgZone } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PopupServiceService } from './serivces/popup-service.service';
import { AuthService } from './services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private router: Router, 
    private zone: NgZone,
    private popupService: PopupServiceService,
    private authService: AuthService
  ) {}

  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Math.floor(Date.now() / 1000);
      return currentTime >= payload.exp;
    } catch (error) {
      console.error('Error decoding token:', error);
      return true;
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');

    if (token) {
      // Check token expiration on the client side.
      if (this.isTokenExpired(token)) {
        // Remove the token immediately.
        localStorage.removeItem('token');

        // Use Angular's zone to trigger change detection for UI updates.
        this.zone.run(() => {
          // Show the popup to inform the user.
          this.popupService.showPopup();
          // After a delay, log out and navigate to login.
          setTimeout(() => {
            this.authService.loggedOut();
            this.router.navigate(['/login']);
          }, 3000);
        });

        // Throw error with the specified message.
        return throwError(() => new Error('io.jsonwebtoken.ExpiredJwtException: JWT expired'));
      }
      
      // If the token is still valid, clone the request with the Authorization header.
      const clonedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      
      // Handle server responses and catch errors if the expired JWT is detected.
      return next.handle(clonedReq).pipe(
        catchError((err: HttpErrorResponse) => {
          if (err.error && err.error.message && err.error.message.includes("JWT expired")) {
            this.zone.run(() => {
              this.popupService.showPopup();
              setTimeout(() => {
                this.authService.loggedOut();
                this.router.navigate(['/login']);
              }, 3000);
            });

            return throwError(() => new Error('io.jsonwebtoken.ExpiredJwtException: JWT expired'));
          }
          return throwError(() => err);
        })
      );
    }

    // If no token exists, simply pass on the request.
    return next.handle(req);
  }
}
