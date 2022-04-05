import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Food} from "../models/food.model";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-add-menu',
  templateUrl: './add-menu.component.html',
  styleUrls: ['./add-menu.component.css']
})
export class AddMenuComponent{

  food: Food ={
    name: '',
    description: '',
    price: '',
    category:'',
    restaurant: ''
  }

  submitted = false;
  failed = false;

  errorMessage = '';

  constructor(private userService: UserService, private app:AppComponent) { }

  ngOnInit(): void {

    this.getRestaurant(this.app.adminName);
  }

  getRestaurant(name: string): void {
    this.userService.getRestaurantName(name)
      .subscribe({
        next: (data) => {
          this.food.restaurant=data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }


  saveFood(): void {
    const data = {
      name: this.food.name,
      description: this.food.description,
      price: this.food.price,
      category: this.food.category,
      restaurant: this.food.restaurant,
    };

    this.userService.createFood(data).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted = true;
      },
      error: (e) => {
        this.failed = true;
        this.errorMessage = e.error.message;
      }
    });
  }

}
