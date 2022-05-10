import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Restaurant} from "../models/restaurant.model";
import {AppComponent} from "../app.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-restaurant',
  templateUrl: './add-restaurant.component.html',
  styleUrls: ['./add-restaurant.component.css']
})
export class AddRestaurantComponent {

  restaurant: Restaurant ={
    name: '',
    location: '',
    delivery_zones: '',
    administrator: ''
  }

  submitted = false;
  failed = false;

  errorMessage = '';

  constructor(private userService: UserService, public app: AppComponent, private router: Router) { }

  saveRestaurant(): void {
    const data = {
      name: this.restaurant.name,
      location: this.restaurant.location,
      delivery_zones: this.restaurant.delivery_zones,
      administrator: this.restaurant.administrator,
      submitted : true,
    };

    this.userService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted = true;
      },
      error: (e) => {
        this.errorMessage = e.error.message;
        this.failed = true;
      }
    });
  }

  functionOnWhichRedirectShouldHappen(){
    this.router.navigate(['**']);
  }

}
