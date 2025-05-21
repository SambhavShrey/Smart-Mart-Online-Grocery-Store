import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-uservieworders',
  templateUrl: './uservieworders.component.html',
  styleUrls: ['./uservieworders.component.css']
})
export class UserviewordersComponent implements OnInit {
  page: number = 1;
  orders: Order[] = [];
  selectedOrder: Order;
  userId:any;
  showPopup: boolean = false;
   popupTitle: string = '';
   popupMessage: string = '';
   popupOnConfirm: () => void;
   popupOnCancel: () => void;
  constructor(private readonly orderService:OrderService, private readonly router : Router) { }

  ngOnInit(): void {
    this.userId= localStorage.getItem('userId')
    this.getOrderByUserId(this.userId)
  }
  deleteOrder(orderId: number): void {
    this.showPopupMsg("Confirm Delete", "Are you sure you want to delete this order?");
    
    // Define confirmation and cancellation actions
    this.popupOnConfirm = () => {
      this.orderService.deleteOrder(orderId).subscribe(() => {
        console.log('Order deleted');
        this.getOrderByUserId(this.userId); // Refresh the orders list
        this.showPopupMsg("Success", "Order deleted successfully!"); // Display success popup
      });
      this.closePopup(); // Close the confirmation popup after clicking "Yes"
    };
    this.popupOnCancel = () => {
      console.log('Order deletion canceled');
      this.closePopup(); // Close the confirmation popup after clicking "No"
    };
  }

  getOrderDetails(orderId: number): void {
    this.orderService.getOrderDetails(orderId).subscribe(data => {
      this.selectedOrder = data;
      console.log('Full Order Details:', JSON.stringify(this.selectedOrder, null, 2));
    });
  }

  getOrderByUserId(userId: number): void {
    this.orderService.getOrderByUserId(userId).subscribe(data => {
       this.orders = data;
      console.log('Orders for user:', data);
    });
  } 

  onPopupConfirm(): void {
    if (this.popupOnConfirm) {
      // Execute the confirm action if it exists
      this.popupOnConfirm();
    }
    this.closePopup(); // Close the popup after confirmation
  }
  
  onPopupCancel(): void {
    if (this.popupOnCancel) {
      // Execute the cancel action if it exists
      this.popupOnCancel();
    }
    this.closePopup(); // Close the popup after cancellation
  }

  // Helper method to show the custom popup
  showPopupMsg(title: string, message: string): void {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
  }

  // Call this method when the user closes the popup
  closePopup(): void {
    this.showPopup = false;
    // If login was successful, navigate to the home page
    if (this.popupTitle === "Success") {
      this.router.navigate(['/viewuserorders']);
    }
  }
}
