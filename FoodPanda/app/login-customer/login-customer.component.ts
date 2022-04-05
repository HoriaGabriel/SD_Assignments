import { Component, OnInit } from '@angular/core';
import {Administrator} from "../models/administrator.model";
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-login-customer',
  templateUrl: './login-customer.component.html',
  styleUrls: ['./login-customer.component.css']
})
export class LoginCustomerComponent implements OnInit {

  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  administrator?: Administrator;

  constructor(private userService: UserService, private app: AppComponent) { }

  ngOnInit(): void {

  }

  onSubmit(): void {

    const { username, password } = this.form;

    this.userService.loginCustomer(username,password).subscribe({
      next: data => {

        this.app.isLoggedCustomer=true;
        this.app.customerName=username;
        this.administrator=data;
        this.isLoginFailed = false;
        this.isLoggedIn = true;
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });
  }

  reloadPage(): void {
    window.location.reload();
  }

}
