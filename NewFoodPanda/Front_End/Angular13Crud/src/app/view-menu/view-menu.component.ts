import { Component, OnInit,ViewChild,ElementRef } from '@angular/core';
import {UserService} from "../_services/user.service";
import {Food} from "../models/food.model";
import {AppComponent} from "../app.component";
import jspdf from 'jspdf';
import html2canvas from 'html2canvas';
import {delay} from "rxjs";
import {TokenStorageService} from "../_services/token-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-view-menu',
  templateUrl: './view-menu.component.html',
  styleUrls: ['./view-menu.component.css']
})
export class ViewMenuComponent implements OnInit {

  @ViewChild('htmlData') htmlData!: ElementRef;
  foods?: Food[];
  restaurant= '';
  aux=false;

  constructor(private userService: UserService, private token: TokenStorageService, public app: AppComponent
              ,private router: Router) { }

  ngOnInit(): void {
    this.retrieveRestaurant(this.token.getUser().username);
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

  public openPDF(): void {
    let DATA: any = document.getElementById('htmlData');
    html2canvas(DATA).then((canvas) => {
      let fileWidth = 450;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jspdf('p', 'mm', 'a5');
      let position = 0;
      PDF.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight);
      PDF.save('Menu.pdf');
    });
  }

  functionOnWhichRedirectShouldHappen(){
    this.router.navigate(['**']);
  }

}
