import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';


import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-adminaddproduct',
  templateUrl: './adminaddproduct.component.html',
  styleUrls: ['./adminaddproduct.component.css']
})
export class AdminaddproductComponent implements OnInit {
  addProductForm:FormGroup;
  isEdited:boolean=false;
  userId:number
  submitted: boolean = false
   showPopup: boolean = false
   popupTitle: string = ''
   popupMessage: string = ''
  selectedId:number;
  editedProduct:Product;
  constructor(private readonly service : ProductService, private readonly fb : FormBuilder, private readonly router : Router, private readonly route : ActivatedRoute){
    this.addProductForm = this.fb.group({
      name : ['',[Validators.required, Validators.pattern('^[a-zA-Z0-9 ]{3,20}$')]],
      description : ['',[Validators.required, Validators.pattern('[a-zA-Z0-9. ]{3,100}$')]],
      price : ['',[Validators.required, Validators.min(1)]],
      stock : ['',[Validators.required, Validators.min(0),Validators.pattern('^[0-9]+$')]],
      category : ['',[Validators.required]],
      photoImage : [''],
      userId : [0]
    });
   }

  backgroundUrl:string;
  ngOnInit(): void {
    //this.userId=+ localStorage.getItem('userId')
    const storedUserId = localStorage.getItem('userId');
    this.userId = storedUserId ? parseInt(storedUserId, 10) : 0;
    this.selectedId=this.route.snapshot.params['id'];
    this.service.getProductById(this.selectedId).subscribe((data)=>{
      this.isEdited=true;
      this.editedProduct=data;
      this.addProductForm.patchValue(this.editedProduct); // Populate form with product
    })
  }

  createProduct() {
    // Attach the userId to the form value
    this.addProductForm.value.userId = this.userId;
  
    if (!this.selectedId) {
      // Add Product Logic
      this.handleProductCreation();
    } else {
      // Edit Product Logic
      this.handleProductUpdate();
    }
  }
  
  handleProductCreation() {
    if (this.addProductForm.valid) {
      this.service.createProduct(this.addProductForm.value).subscribe(
        () => {
          console.log('Product added:', this.addProductForm.value);
          this.showPopupMsg("Success", "Product added successfully!!!");
          this.addProductForm.reset();
        },
        (error) => {
          console.error('Error adding product:', error);
          this.showPopupMsg("Error", "Product failed to add!!!");
        }
      );
    } else {
      this.addProductForm.markAllAsTouched();
    }
  }
  
  handleProductUpdate() {
    if (this.addProductForm.valid) {
      this.service.updateProduct(this.selectedId, this.addProductForm.value).subscribe(
        () => {
          console.log('Product updated:', this.addProductForm.value);
          this.showPopupMsg("Success", "Product updated successfully!!!");
          this.isEdited = false;
          this.addProductForm.reset();
          this.router.navigate(['/viewproduct']);
        },
        (error) => {
          console.error('Error updating product:', error);
          this.showPopupMsg("Error", "Product failed to update!!!");
        }
      );
    } else {
      this.addProductForm.markAllAsTouched();
    }
  }
  
  
 
  onFileChange(event: Event, fileType: string): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0]; // Get the selected file
      const reader = new FileReader();
      
      reader.onload = () => {
        if (fileType === 'photoImage') {
          console.log('Photo added');
          // Update the FormControl value with the base64 string
          this.addProductForm.get('photoImage')?.setValue(reader.result as string);
          console.log(this.addProductForm.get('photoImage').value)
        }
      };
  
      reader.readAsDataURL(file); // Convert the file to Base64 format
    }
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
        this.router.navigate(['/viewproduct']);
      }
    }
}
