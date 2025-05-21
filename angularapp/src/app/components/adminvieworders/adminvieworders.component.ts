import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-adminvieworders',
  templateUrl: './adminvieworders.component.html',
  styleUrls: ['./adminvieworders.component.css']
})
export class AdminviewordersComponent implements OnInit, OnDestroy {
  orders: Order[] = [];
  selectedOrder!: Order;
  userId!: number;
  
  // Popup properties
  showConfirmationPopup = false;
  showErrorPopup = false;
  confirmationPopupTitle = '';
  confirmationPopupMessage = '';
  errorPopupTitle = '';
  errorPopupMessage = '';
  selectedOrderIdForUpdate!: number;
  newStatusForUpdate!: string;
  page: number = 1;

  constructor(private readonly orderService: OrderService,private readonly route:Router) {}

  ngOnInit(): void {
    this.setBackgroundImage();
    this.getAllOrders();
  }

  ngOnDestroy(): void {
    this.resetBackgroundImage();
  }

  private setBackgroundImage(): void {
    document.body.style.background = "url('https://img.freepik.com/free-vector/flat-world-post-day-background_23-2149084335.jpg?t=st=1746012660~exp=1746016260~hmac=169799412ad65a722e5b45f37395833bc1543af5991a100f93556ecc81b26109&w=1380') no-repeat center center fixed";
    document.body.style.backgroundSize = "cover";
  }

  private resetBackgroundImage(): void {
    document.body.style.background = '';
  }

  private handleError(title: string, message: string): void {
    this.errorPopupTitle = title;
    this.errorPopupMessage = message;
    this.showErrorPopup = true;
  }

  getOrdersByUserId(): void {
    if (this.userId) {
      this.orderService.getOrderByUserId(this.userId).subscribe(
        data => this.orders = data,
        error => this.handleError('Fetch Failed', 'Unable to retrieve orders.')
      );
    } else {
      this.getAllOrders();
    }
  }

  getAllOrders(): void {
    this.orderService.getOrders().subscribe(
      data => this.orders = data,
      error => this.handleError('Fetch Failed', 'Unable to retrieve orders.')
    );
  }

  getOrderDetails(orderId: number): void {
    this.orderService.getOrderDetails(orderId).subscribe(
      data => this.selectedOrder = data,
      error => this.handleError('Details Fetch Failed', 'Unable to retrieve order details.')
    );
  }

  openConfirmationPopup(orderId: number, status: string): void {
    this.selectedOrderIdForUpdate = orderId;
    this.newStatusForUpdate = status;
    this.confirmationPopupTitle = 'Confirm Status Update';
    this.confirmationPopupMessage = `Are you sure you want to mark Order #${orderId} as ${status}?`;
    this.showConfirmationPopup = true;
  }

  closeConfirmationPopup(): void {
    this.showConfirmationPopup = false;
  }

  confirmUpdate(): void {
    this.orderService.updateOrderStatus(this.selectedOrderIdForUpdate, this.newStatusForUpdate).subscribe(
      updatedOrder => {
        const orderIndex = this.orders.findIndex(order => order.orderId === this.selectedOrderIdForUpdate);
        if (orderIndex !== -1) {
          this.orders[orderIndex].status = updatedOrder.status;
        }
        this.closeConfirmationPopup();
      },
      error => {
        this.closeConfirmationPopup();
        this.handleError('Update Failed', 'Failed to update order status. Please try again later.');
      }
    );
  }

  closeErrorPopup(): void {
    this.showErrorPopup = false;
    this.route.navigate(['/viewproduct'])
  }
}
