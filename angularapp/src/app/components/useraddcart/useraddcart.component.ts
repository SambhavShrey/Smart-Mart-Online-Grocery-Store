import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from 'src/app/models/order.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-useraddcart',
  templateUrl: './useraddcart.component.html',
  styleUrls: ['./useraddcart.component.css']
})
export class UseraddcartComponent implements OnInit {
  orderForm: FormGroup;
  orders: Order[] = [];
  order!: Order;
  // Popup properties
  showPopup: boolean = false;
  popupTitle: string = '';
  popupMessage: string = '';
  userId: any;
  products: any[] = [];

  constructor(
    private readonly orderService: OrderService,
    private readonly fb: FormBuilder,
    private readonly cartServive: CartService,
    private readonly router: Router
  ) { }

  clearCart(): void {
    this.cartServive.clearCart();
    this.orderForm.reset();
    localStorage.setItem('cart','')
    this.showPopupMsg("Clear", "Cart cleared successfully!!!");
  }

  ngOnInit(): void {
    this.orderForm = this.fb.group({
      shippingAddress: ['', [Validators.required]],
      userId: [null],
      productList: [[]]
    });
    this.userId = localStorage.getItem('userId');
  }

  placeOrder(): void {
    // Check if the cart is empty. If yes, display an error popup.
    if (this.cartItems.length === 0) {
      this.showPopupMsg("Error", "Your cart is empty. Please add items before placing an order.");
      return;
    }
    if (this.orderForm.valid) {
      // Append the current cart items to the order form
      this.orderForm.value.productList = this.cartServive.getCartItems();
      this.orderForm.value.userId = this.userId;
      this.orderService.placeOrder(this.orderForm.value).subscribe(
        data => {
          console.log('Order placed successfully:', data);
          this.clearCart();
          this.showPopupMsg("Success", "Order placed successfully!!!");
        },
        error => {
          console.error('Order placement error:', error);
          this.showPopupMsg("Error", "Order placement failed!!!");
        }
      );
    } else {
      // Mark all controls as touched to trigger validation messages.
      this.orderForm.markAllAsTouched();
    }
  }

  // Getter to obtain the current cart items from the service
  get cartItems(): any[] {
    return this.cartServive.getCartItems();
  }

  // Helper method to show the custom popup
  showPopupMsg(title: string, message: string): void {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
  }

  // Closes the popup and navigates upon success
  closePopup(): void {
    this.showPopup = false;
    // Navigate to "/viewuserorders" only if the order was placed successfully.
    if (this.popupTitle === "Success") {
      this.router.navigate(['/viewuserorders']);
    } else if (this.popupTitle == "Clear") {
      this.router.navigate(['/']);
    }
  }
}