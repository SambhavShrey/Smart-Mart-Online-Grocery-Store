import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registerForm: FormGroup;
  submitted: boolean = false;
   // Popup properties
   showPopup: boolean = false
   popupTitle: string = ''
   popupMessage: string = ''
  
  // Static full-screen background image URL
  backgroundUrl: string = 'https://img.freepik.com/free-vector/ecommerce-web-store-hand-drawn-illustration_107791-10966.jpg?t=st=1745824723~exp=1745828323~hmac=52082271ee07d43fbefa9c91f1c5d142c9317583b3b72227bece9c63f8d5b5f6&w=2000';

  constructor(private readonly formBuilder: FormBuilder,private readonly service:AuthService) {}

  ngOnInit(): void {
    // Build the reactive form with validations
    this.registerForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['',[Validators.required,Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$')]],
      confirmPassword: ['', Validators.required],
      username: ['',[Validators.required, Validators.maxLength(50)]],
      mobileNumber: ['',[Validators.required, Validators.pattern("^\\d{10}$")]]
    },{ validators: this.passwordMatchValidator });
  }
  //Check if password and confirmPassword matches
  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  // Convenient getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  // Handle form submission
  onSubmit(): void {
    this.submitted = true;
    if (this.registerForm.valid) {
      this.service.registerUser(this.registerForm.value).subscribe(()=>{
        this.showPopupMsg("Success", "Registration Successful!!!");
      },(error)=>{
          this.showPopupMsg("Error", "Opps, Something went wrong!!");
          console.log("Error: "+JSON.stringify(error))
      })
    }
    //If Form value is Invalid
    else
      this.showPopupMsg("Error", "Invalid Form Input");
  }
  onReset(): void {
    this.submitted = false;
    this.registerForm.reset();
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
        this.onReset()
    }
  }

}
