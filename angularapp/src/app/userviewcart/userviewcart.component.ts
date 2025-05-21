import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { Router } from '@angular/router';
import { ProductService } from '../services/product.service';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-userviewcart',
  templateUrl: './userviewcart.component.html',
  styleUrls: ['./userviewcart.component.css'],
  animations: [
    trigger('listAnimation', [
      transition('* => *', [
        query(':enter', [
          style({ opacity: 0, transform: 'translateY(25px)' }),
          stagger('100ms', [
            animate('500ms ease-out', style({ opacity: 1, transform: 'translateY(0px)' }))
          ])
        ], { optional: true }),
        query(':leave', [
          stagger('100ms', [
            animate('500ms ease-out', style({ opacity: 0, transform: 'translateY(25px)' }))
          ])
        ], { optional: true })
      ])
    ]),
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms ease-out', style({ opacity: 1 }))
      ])
    ])
  ]
})
export class UserviewcartComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  cartLoading: boolean = true;
  private readonly subscriptions: Subscription = new Subscription();
  totalAmount: number = 0;

  constructor(
    private readonly cartService: CartService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly cd: ChangeDetectorRef // Inject ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadCartItems();
  }

  loadCartItems(): void {
    const productIds = this.cartService.getCartItems();
    this.cartLoading = true;
    
    if (productIds.length === 0) {
      this.cartLoading = false;
      return;
    }

    // Load each product by ID
    productIds.forEach(id => {
      const subscription = this.productService.getProductById(id).subscribe(
        (product: Product) => {
          this.products.push(product);
          this.calculateTotal();
          
          // Check if all products are loaded
          if (this.products.length === productIds.length) {
            this.cartLoading = false;
            this.cd.detectChanges(); // Force update after initial load if needed
          }
        },
        (error) => {
          console.error('Error loading product:', error);
          this.cartLoading = false;
        }
      );
      this.subscriptions.add(subscription);
    });
  }

  calculateTotal(): void {
    this.totalAmount = this.products.reduce((sum, product) => sum + product.price, 0);
  }

  deleteProductFromCart(index: number): void {
    // Add animation class before removal
    const productElements = document.querySelectorAll('.product-card');
    const productElement = productElements[index];
    if (productElement) {
      productElement.classList.add('animate__animated', 'animate__fadeOutRight');
      
      // Wait for animation to complete before removing from array
      setTimeout(() => {
        this.cartService.deleteCart(index);
        this.products.splice(index, 1);
        this.calculateTotal();
        // Create a new array reference to force change detection
        this.products = [...this.products];
        this.cd.detectChanges();
      }, 500);
    } else {
      this.cartService.deleteCart(index);
      this.products.splice(index, 1);
      this.calculateTotal();
      this.products = [...this.products];
      this.cd.detectChanges();
    }
  }

  redirectToViewProducts(): void {
    this.router.navigate(['/viewproduct']);
  }

  redirectToCart(): void {
    this.router.navigate(['/gotocart']);
  }

  clearCart(): void {
    localStorage.setItem('cart', '');
    // Add animation class before clearing
    const productElements = document.querySelectorAll('.product-card');
    productElements.forEach(element => {
      element.classList.add('animate__animated', 'animate__zoomOut');
    });
    
    // Wait for animation to complete before clearing
    setTimeout(() => {
      this.cartService.clearCart();
      this.products = [];
      this.totalAmount = 0;
      this.cd.detectChanges();
    }, 500);
  }

  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this.subscriptions.unsubscribe();
  }
}
