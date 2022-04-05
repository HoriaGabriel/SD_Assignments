import {Component, Input, OnInit} from '@angular/core';
import {Food} from "../models/food.model";
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";
import {Restaurant} from "../models/restaurant.model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-restaurant-menu',
  templateUrl: './restaurant-menu.component.html',
  styleUrls: ['./restaurant-menu.component.css']
})
export class RestaurantMenuComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentRestaurant: Restaurant = {
    location: '',
    delivery_zones: '',
    administrator: '',
  };

  foods?: Food[];
  aux=false;

  constructor(private userService: UserService,private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {

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
