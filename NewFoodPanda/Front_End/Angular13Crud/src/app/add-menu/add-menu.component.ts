import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Food} from "../models/food.model";
import {AppComponent} from "../app.component";
import {TokenStorageService} from "../_services/token-storage.service";
import {Router} from "@angular/router";

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

  constructor(private userService: UserService, private token: TokenStorageService, public app: AppComponent
              ,private router: Router) { }

  ngOnInit(): void {
    this.getRestaurant(this.token.getUser().username);
  }

  getRestaurant(name: string): void {
    this.userService.getRestaurantName(name)
      .subscribe({
        next: (data) => {
          this.food.restaurant=data;
          console.log(data);
        },
        error: (e) => {
          console.log("getRestaurantFails");
          console.error(e)
        }
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

  functionOnWhichRedirectShouldHappen(){
    this.router.navigate(['**']);
  }

}
