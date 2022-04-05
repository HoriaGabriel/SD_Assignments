import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";
import {Order} from "../models/order.model";

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrls: ['./view-orders.component.css']
})
export class ViewOrdersComponent implements OnInit {

  orders?: Order[];
  restaurant= '';
  aux=false;

  constructor(private userService: UserService, public app: AppComponent) { }

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

}
