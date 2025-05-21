import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LogoutComponent } from './components/logout/logout.component';
import { AdminaddproductComponent } from './components/adminaddproduct/adminaddproduct.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { AdminviewproductComponent } from './components/adminviewproduct/adminviewproduct.component';
import { UserviewordersComponent } from './components/uservieworders/uservieworders.component';
import { UseraddcartComponent } from './components/useraddcart/useraddcart.component';
import { AdminviewordersComponent } from './components/adminvieworders/adminvieworders.component';
import { UserviewcartComponent } from './userviewcart/userviewcart.component';
import { LoggedInGuard } from './logged-in.guard';
import { UserGuard } from './user.guard';
import { AdminGuard } from './admin.guard';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegistrationComponent},
  {path:'logout',component:LogoutComponent, canActivate:[LoggedInGuard]},
  {path:'addproduct',component:AdminaddproductComponent, canActivate:[AdminGuard]},
  {path:'addproduct/:id',component:AdminaddproductComponent, canActivate:[AdminGuard]},
  {path:'viewproduct',component:AdminviewproductComponent, canActivate:[LoggedInGuard]},
  {path:'add-feedback',component:UseraddfeedbackComponent, canActivate:[UserGuard]},
  {path:'my-feedbacks',component:UserviewfeedbackComponent, canActivate:[UserGuard]},
  {path:'view-feedbacks',component:AdminviewfeedbackComponent, canActivate:[AdminGuard]},
  {path:'viewuserorders',component:UserviewordersComponent, canActivate:[UserGuard]},
  {path:'gotocart',component:UseraddcartComponent, canActivate:[UserGuard]},
  {path:'adminvieworders',component:AdminviewordersComponent, canActivate:[AdminGuard]},
  {path:'viewusercart',component:UserviewcartComponent, canActivate:[UserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
