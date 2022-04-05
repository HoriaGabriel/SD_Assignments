import { Component, OnInit } from '@angular/core';
import {Order} from "../models/order.model";
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css']
})
export class AddToCartComponent implements OnInit {

  order: Order = {
    client: '',
    food: ''
  };
  submitted = false;
  failed=false;

  errorMessage = '';

  constructor(private userService: UserService, public app: AppComponent) { }

  ngOnInit(): void {
  }

  addFoodProduct(): void {

    const data = {
      client: this.app.customerName,
      food: this.order.food,
    };

    this.userService.addFoodToOrder(data).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted = true;
      },
      error: (e) => {
        this.failed=true;
        this.errorMessage = e.error.message;
      }
    });
  }

  newOrder(): void {
    this.submitted = false;
    this.order = {
      client: '',
      food: '',
    };
  }

}
