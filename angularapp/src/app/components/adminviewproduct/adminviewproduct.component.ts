import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-adminviewproduct',
  templateUrl: './adminviewproduct.component.html',
  styleUrls: ['./adminviewproduct.component.css']
})
export class AdminviewproductComponent implements OnInit {
  products: any[] = [];
  filteredProducts: any[] = [];
  searchTerm: string = '';
  selectedCategory: string = '';
  showPopup: boolean = false;
  popupTitle: string = '';
  popupMessage: string = '';
  confirmDelete: boolean = false;
  productIdToDelete: number | null = null;
  isCartEmpty: boolean = true;
  isLoading: boolean = true;
  currentPage: number = 1;
  itemsPerPage: number = 8; // Number of products per page

  constructor(
    private readonly productService: ProductService, 
    private readonly router: Router, 
    public readonly authService: AuthService, 
    private readonly cartService: CartService
  ) { }

  ngOnInit(): void {
    this.getAllProducts();
    this.setCart();
    this.checkCartStatus();
  }
  onPageChange(page: number): void {
    this.currentPage = page;
  }

  setCart() {
    if (this.cartService.getCartItems().length === 0) {
      this.cartService.addMultipleProductsToCart(JSON.parse(localStorage.getItem('cart') || '[]'));
    }
  }

  getAllProducts(): void {
    this.isLoading = true;
    this.productService.getProducts().subscribe(
      (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.isLoading = false;
      },
      (error) => {
        console.error('Error fetching products:', error);
        this.isLoading = false;
        this.showPopupMsg('Error', 'Failed to load products. Please try again later.');
      }
    );
  }

  checkCartStatus(): void {
    this.isCartEmpty = this.cartService.getCartItems().length === 0;
  }

  filterProducts(): void {
    this.filteredProducts = this.products.filter(product => 
      product.name.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
      (this.selectedCategory === '' || product.category === this.selectedCategory)
    );
  }

  editProduct(productId: number): void {
    this.router.navigate(['/addproduct', productId]);
  }

  confirmDeleteProduct(productId: number): void {
    this.productIdToDelete = productId;
    this.showPopupMsg('Confirm Deletion', 'Are you sure you want to delete this product?');
    this.confirmDelete = true;
  }

  deleteProduct(): void {
    if (this.productIdToDelete !== null) {
      this.productService.deleteProduct(this.productIdToDelete).subscribe(
        () => {
          this.products = this.products.filter(product => product.productId !== this.productIdToDelete);
          this.filteredProducts = this.filteredProducts.filter(product => product.productId !== this.productIdToDelete);
          this.showPopupMsg('Success', 'Product deleted successfully!');
          this.getAllProducts();
        }, 
        error => {
          this.showPopupMsg('Error', 'Failed to delete the product. Please try again.');
        }
      );
    }
    this.confirmDelete = false;
  }

  addToCart(id: number): void {
    this.showPopupMsg('Success', 'Product added to Cart!');
    this.cartService.addProductToCart(id);
    this.checkCartStatus();
  }

  redirectToCart(): void {
    this.router.navigate(['/viewusercart']);
  }

  showPopupMsg(title: string, message: string): void {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
    
    // Auto-close success messages after 2 seconds
    if (title === 'Success') {
      setTimeout(() => {
        this.showPopup = false;
      }, 2000);
    }
  }

  closePopup(confirm: boolean = false): void {
    this.showPopup = false;
    if (confirm && this.confirmDelete) {
      this.deleteProduct();
    }
    this.confirmDelete = false;
  }
}