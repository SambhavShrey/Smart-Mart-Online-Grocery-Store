import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Api } from 'src/api';
import { Order } from '../models/order.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  apiUrl:string=Api.apiUrlUser

  constructor(private readonly http:HttpClient) { }
  
  placeOrder(order:Order):Observable<Order>{
      return this.http.post<Order>(`${this.apiUrl}/orders/add`,order)
    }
    deleteOrder(orderId:number):Observable<void>{
      return this.http.delete<void>(`${this.apiUrl}/orders/${orderId}`)
    }
    getOrderDetails(orderId:number):Observable<Order>{
      return this.http.get<Order>(`${this.apiUrl}/orders/${orderId}`)
    }
    getOrderByUserId(userId:number):Observable<Order[]>{
      return this.http.get<Order[]>(`${this.apiUrl}/orders/user/${userId}`)
    }
    getOrders():Observable<Order[]>{
      return this.http.get<Order[]>(`${this.apiUrl}/orders`)
    }
    updateOrderStatus(id:number,order:any):Observable<Order>{
      return this.http.put<Order>(`${this.apiUrl}/orders/${id}`,order)
    }
}
