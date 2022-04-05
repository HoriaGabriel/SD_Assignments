import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Restaurant} from "../models/restaurant.model";
import {Food} from "../models/food.model";

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {

  restaurants?: Restaurant[];
  currentRestaurant: Restaurant = {};
  currentIndex = -1;
  name = '';

  aux=false;

  foods?: Food[];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.retrieveRestaurants();
  }

  retrieveRestaurants(): void {
    this.userService.getAllRestaurants()
      .subscribe({
        next: (data) => {
          this.restaurants = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  refreshList(): void {
    this.retrieveRestaurants();
    this.currentRestaurant = {};
    this.currentIndex = -1;
  }

  setActiveRestaurant(restaurant: Restaurant, index: number): void {
    this.currentRestaurant = restaurant;
    this.currentIndex = index;
  }

  searchRestaurant(): void {
    this.currentRestaurant = {};
    this.currentIndex = -1;

    this.userService.getRestaurant(this.name)
      .subscribe({
        next: (data) => {
          this.restaurants = data;
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
          this.aux=true;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

}
