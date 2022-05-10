import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Order} from "../models/order.model";
import {TokenStorageService} from "../_services/token-storage.service";
import {AppComponent} from "../app.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrls: ['./view-orders.component.css']
})
export class ViewOrdersComponent implements OnInit {

  orders?: Order[];
  restaurant= '';
  aux=false;
  client = this.token.getUser().username;

  constructor(private userService: UserService, private token: TokenStorageService, public app: AppComponent
              ,private router: Router) { }

  ngOnInit(): void {
  }

  retrieveOrders(name: string): void {
    this.userService.getCustomerOrders(name)
      .subscribe({
        next: (data) => {
          this.orders = data;
          this.aux=true;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  functionOnWhichRedirectShouldHappen(){
    this.router.navigate(['**']);
  }

}
