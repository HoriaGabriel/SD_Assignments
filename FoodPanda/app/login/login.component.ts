import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };

  adminName= '';
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  constructor(private userService: UserService, private app: AppComponent) { }

  ngOnInit(): void {

  }

  onSubmit(): void {

    const { username, password } = this.form;

    this.userService.login(username,password).subscribe({
      next: data => {

        this.app.isLoggedAdmin=true;
        this.app.adminName=username;
        this.adminName = username;
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
