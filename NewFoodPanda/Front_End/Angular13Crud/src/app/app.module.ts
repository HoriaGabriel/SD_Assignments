import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LoginCustomerComponent } from './login-customer/login-customer.component';
import { SetUpComponent } from './set-up/set-up.component';
import { AddRestaurantComponent } from './add-restaurant/add-restaurant.component';

import { AddMenuComponent } from './add-menu/add-menu.component';
import { ViewMenuComponent } from './view-menu/view-menu.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { RestaurantMenuComponent } from './restaurant-menu/restaurant-menu.component';
import { AddToCartComponent } from './add-to-cart/add-to-cart.component';
import { MakeOrderComponent } from './make-order/make-order.component';
import { ViewOrdersComponent } from './view-orders/view-orders.component';
import { ViewOrdersAdminComponent } from './view-orders-admin/view-orders-admin.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LoginCustomerComponent,
    SetUpComponent,
    AddRestaurantComponent,
    AddMenuComponent,
    ViewMenuComponent,
    RestaurantListComponent,
    RestaurantMenuComponent,
    AddToCartComponent,
    MakeOrderComponent,
    ViewOrdersComponent,
    ViewOrdersAdminComponent,
    PagenotfoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
