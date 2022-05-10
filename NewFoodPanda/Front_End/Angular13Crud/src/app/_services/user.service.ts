import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Food} from "../models/food.model";
import {Restaurant} from "../models/restaurant.model";
import {Order} from "../models/order.model";


const API_URL1 = 'http://localhost:8080/api/home';
const API_URL2 = 'http://localhost:8080/api/login';
const API_URL3 = 'http://localhost:8080/api/login-customer';
const API_URL4 = 'http://localhost:8080/api/set-up';
const API_URL5 = 'http://localhost:8080/api3/add-restaurant';
const API_URL6 = 'http://localhost:8080/api/get-restaurant';
const API_URL7 = 'http://localhost:8080/api4/add-food';
const API_URL8 = 'http://localhost:8080/api3/get-foods';
const API_URL9 = 'http://localhost:8080/api3/get-all-restaurants';
const API_URL10 = 'http://localhost:8080/api3/get-full-restaurant';
const API_URL11 = 'http://localhost:8080/api4/add-to-cart';
const API_URL12 = 'http://localhost:8080/api4/get-price';
const API_URL13 = 'http://localhost:8080/api4/make-order';
const API_URL14 = 'http://localhost:8080/api4/get-order';
const API_URL15 = 'http://localhost:8080/api/get-client-orders';
const API_URL16 = 'http://localhost:8080/api/get-admin-orders';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL1, { responseType: 'text' });
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(API_URL2, {username, password}, httpOptions);
  }

  loginCustomer(username: string, password: string): Observable<any> {
    return this.http.post(API_URL3, {username, password}, httpOptions);
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(API_URL4, {username, email, password}, httpOptions);
  }

  create(data: any): Observable<any> {
    return this.http.post(API_URL5, data, httpOptions);
  }

  getRestaurantName(name: string): Observable<string> {
    return this.http.get(`${API_URL6}?name=${name}`,{responseType: 'text'});
  }

  createFood(data: any): Observable<any> {
    return this.http.post(API_URL7, data, httpOptions);
  }

  getFoodsByRestaurant(restaurant: string): Observable<Food[]> {
    return this.http.get<Food[]>(`${API_URL8}?restaurant=${restaurant}`);
  }

  getAllRestaurants(): Observable<Restaurant[]>{
    return this.http.get<Restaurant[]>(API_URL9);
  }

  getRestaurant(name: string): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(`${API_URL10}?name=${name}`);
  }

  addFoodToOrder(data: any): Observable<any> {
    return this.http.post(API_URL11, data, httpOptions);
  }

  getPrice(): Observable<string> {
    return this.http.get<string>(API_URL12);
  }

  makeOrder(client: string): Observable<any>{
    return this.http.put(`${API_URL13}?client=${client}`,httpOptions);
  }

  getOrders(): Observable<string[]> {
    return this.http.get<string[]>(API_URL14);
  }

  getCustomerOrders(name: string): Observable<Order[]> {
    return this.http.get<Order[]>(`${API_URL15}?name=${name}`);
  }

  getAdminOrders(name: string): Observable<Order[]> {
    return this.http.get<Order[]>(`${API_URL16}?name=${name}`);
  }
}
