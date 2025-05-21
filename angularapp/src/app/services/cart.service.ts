import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private products: any[] = [];

  constructor() {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.products = JSON.parse(storedCart);
    }
  }

  private updateStorage(): void {
    localStorage.setItem('cart', JSON.stringify(this.products));
  }

  addProductToCart(product: any): void {
    this.products.push(product);
    this.updateStorage();
    console.log('Product added:', product);
  }

  deleteCart(index: number): void {
    if (index >= 0 && index < this.products.length) {
      const removedProduct = this.products.splice(index, 1);
      this.updateStorage();
      console.log('Product removed:', removedProduct);
    } else {
      console.log('Invalid index:', index);
    }
  }

  addMultipleProductsToCart(products: any[]): void {
    this.products = [...this.products, ...products];
    this.updateStorage();
    console.log('Products added:', products);
  }

  clearCart(): void {
    this.products = [];
    localStorage.removeItem('cart');
    console.log('Cart cleared');
  }

  getCartItems(): any[] {
    return this.products;
  }
}
