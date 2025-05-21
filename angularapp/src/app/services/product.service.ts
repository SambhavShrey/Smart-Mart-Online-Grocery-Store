import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { Api } from 'src/api';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  apiUrl:string=Api.apiUrlUser
  constructor(private readonly http : HttpClient) { }
  createProduct(product : Product) : Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/products/add-product`,product);
  }
  getProducts() : Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products`);
  }
  getProductById(productId : number) : Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/products/${productId}`);
  }
  getProductsByUserId(userId : number) : Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/user/${userId}`);
  }
  getProductsByCategory(category : string) : Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/category/${category}`);
  }
  updateProduct(productId : number, updatedProduct : Product) : Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/products/edit-product/${productId}`,updatedProduct);
  }
  deleteProduct(productId : number) : Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/products/${productId}`);
  }
  
}
