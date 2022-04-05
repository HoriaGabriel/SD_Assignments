import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Food} from "../models/food.model";
import {AppComponent} from "../app.component";
import {delay} from "rxjs";

@Component({
  selector: 'app-view-menu',
  templateUrl: './view-menu.component.html',
  styleUrls: ['./view-menu.component.css']
})
export class ViewMenuComponent implements OnInit {

  foods?: Food[];
  restaurant= '';
  aux=false;

  constructor(private userService: UserService, public app: AppComponent) { }

  ngOnInit(): void {
    this.retrieveRestaurant(this.app.adminName);
  }

  retrieveRestaurant(name: string): void {
    this.userService.getRestaurantName(name)
      .subscribe({
        next: (data) => {
          this.restaurant = data;
          this.aux=true;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  retrieveFoods(restaurant: string): void {
    this.userService.getFoodsByRestaurant(restaurant)
      .subscribe({
        next: (data) => {
          this.foods = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

}
