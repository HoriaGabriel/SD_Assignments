import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services/user.service";
import {AppComponent} from "../app.component";
import {TokenStorageService} from "../_services/token-storage.service";
import { Router } from '@angular/router';


@Component({
  selector: 'app-make-order',
  templateUrl: './make-order.component.html',
  styleUrls: ['./make-order.component.css']
})
export class MakeOrderComponent implements OnInit {

  price='';
  client=this.token.getUser().username;

  names: Array<string>=[];

  submitted=false;

  constructor(private userService: UserService, private token: TokenStorageService,
              public app: AppComponent, private router: Router) {}

  ngOnInit(): void {

    this.getOrderNames();
    this.getOrderPrice();

  }

  getOrderNames(): void {

    this.userService.getOrders().subscribe({
      next: (res) => {
        this.names=res;
        console.log(res);
      },
      error: (e) => console.error(e)
    });
  }

  getOrderPrice(): void {


    this.userService.getPrice().subscribe({
      next: (res) => {
        this.price=res;
        console.log(res);
      },
      error: (e) => console.error(e)
    });
  }

  Order(client): void {

    this.userService.makeOrder(client).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted=true;
      },
      error: (e) => console.error(e)
    });
  }

  functionOnWhichRedirectShouldHappen(){
    this.router.navigate(['**']);
  }

}
