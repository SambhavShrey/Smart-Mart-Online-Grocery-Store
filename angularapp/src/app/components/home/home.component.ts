import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

interface GuideItem {
  id: string;
  title: string;
  content: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnDestroy {
  // Custom popup flag for non‑logged‑in users
  showPopup = false;
  
  // Trigger for the animation overlay (for Best Prices)
  showAnimation = false;

  // Guides data for the accordion section
  guides: GuideItem[] = [
    {
      id: 'freshProduce',
      title: 'Guide to Fresh Produce',
      content: 'Learn how to select the freshest produce by checking for vibrant colors, firmness, and aroma. Discover seasonal tips and storage techniques to maximize shelf life and nutrition.'
    },
    {
      id: 'budgetShopping',
      title: 'Budget Shopping',
      content: 'Discover savvy tips for saving money on groceries. Learn how to compare prices, shop seasonal items, and make the most of bulk buying to stick to your budget.'
    },
    {
      id: 'healthyEating',
      title: 'Healthy Eating',
      content: 'Explore nutritional insights and meal planning advice for a balanced lifestyle. Learn how to incorporate a variety of food groups and maintain healthy eating habits with Smart Mart\'s offerings.'
    }
  ];
  
  // Track which guide is open; default to "freshProduce"
  openGuide: string = 'freshProduce';

  // Reference for the animation timer so we can clear it on destroy
  private animationTimer: any;

  constructor(
    private readonly router: Router, 
    private readonly authService: AuthService
  ) { }

  ngOnDestroy(): void {
    if (this.animationTimer) {
      clearTimeout(this.animationTimer);
    }
  }

  viewAll(): void {
    if (this.authService.isLoggedUser()) {
      this.router.navigate(['/viewproduct']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  view(): void {
    if (this.authService.isLoggedUser()) {
      this.router.navigate(['/viewproduct']);
    } else {
      // Show our custom popup instead of a plain alert
      this.showPopup = true;
    }
  }

  closePopup(): void {
    this.showPopup = false;
  }

  toggleGuide(id: string): void {
    this.openGuide = this.openGuide === id ? '' : id;
  }

  triggerAnimation(): void {
    // When the Best Prices card is clicked, show the animation overlay.
    this.showAnimation = true;
    // Hide the animation after 5 seconds (the duration of the keyframes animation).
    this.animationTimer = setTimeout(() => {
      this.showAnimation = false;
    }, 5000);
  }
}
