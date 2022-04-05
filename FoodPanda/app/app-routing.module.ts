import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LoginCustomerComponent } from './login-customer/login-customer.component';
import { SetUpComponent } from './set-up/set-up.component';
import { AddRestaurantComponent} from './add-restaurant/add-restaurant.component';
import { AddMenuComponent } from './add-menu/add-menu.component';
import {ViewMenuComponent} from "./view-menu/view-menu.component";
import {RestaurantListComponent} from "./restaurant-list/restaurant-list.component";
import {RestaurantMenuComponent} from "./restaurant-menu/restaurant-menu.component";
import {AddToCartComponent} from "./add-to-cart/add-to-cart.component";
import {MakeOrderComponent} from "./make-order/make-order.component";
import { ViewOrdersComponent } from './view-orders/view-orders.component';
import { ViewOrdersAdminComponent } from './view-orders-admin/view-orders-admin.component';

const routes: Routes = [

  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'login-customer', component: LoginCustomerComponent },
  { path:'set-up', component: SetUpComponent},
  { path: 'add-restaurant', component: AddRestaurantComponent },
  { path: 'add-menu', component: AddMenuComponent},
  { path: 'view-menu', component: ViewMenuComponent},
  { path: 'restaurant-list', component: RestaurantListComponent},
  { path: 'restaurant-list/:id', component: RestaurantMenuComponent },
  {path: 'add-to-cart', component: AddToCartComponent},
  {path: 'make-order', component: MakeOrderComponent},
  {path: 'view-orders', component: ViewOrdersComponent},
  {path: 'view-orders-admin', component: ViewOrdersAdminComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
