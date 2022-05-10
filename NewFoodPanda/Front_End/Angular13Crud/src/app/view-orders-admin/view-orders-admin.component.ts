import { Component, OnInit } from '@angular/core';
import {Order} from "../models/order.model";
import {UserService} from "../_services/user.service";
import {TokenStorageService} from "../_services/token-storage.service";
import {AppComponent} from "../app.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-view-orders-admin',
  templateUrl: './view-orders-admin.component.html',
  styleUrls: ['./view-orders-admin.component.css']
})
export class ViewOrdersAdminComponent implements OnInit {

  orders?: Order[];
  restaurant= '';
  aux=false;
  adminName=this.token.getUser().username;

  constructor(private userService: UserService, private token: TokenStorageService
              , public app: AppComponent, private router: Router) { }

  ngOnInit(): void {
  }

  retrieveOrders(name: string): void {
    this.userService.getAdminOrders(name)
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
