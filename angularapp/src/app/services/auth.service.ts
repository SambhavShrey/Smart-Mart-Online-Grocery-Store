import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Api } from 'src/api';
import { Login } from '../models/login.model';
import { LoginDto } from '../models/loginDTO.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl:string=Api.apiUrlUser
  constructor(private readonly http:HttpClient) { }
  registerUser(user:User):Observable<User>{
    return this.http.post<User>(`${this.apiUrl}/registers`,user)
  }
  loginUser(login:Login):Observable<LoginDto>{
    return this.http.post<LoginDto>(`${this.apiUrl}/login`,login)
  }
  isAdmin():boolean{
    let role=localStorage.getItem('userRole')
    return role=='ADMIN'
  }
  isUser():boolean{
    let role=localStorage.getItem('userRole')
    return role=='USER'
  }
  isLoggedUser():boolean{
    let role=localStorage.getItem('userRole')
    return role!=null 
  }
  loggedOut():void{
      localStorage.removeItem("username")
      localStorage.removeItem("token")
      localStorage.removeItem("userId")
      localStorage.removeItem("userRole")
      localStorage.removeItem("profilePic")
   }
}
