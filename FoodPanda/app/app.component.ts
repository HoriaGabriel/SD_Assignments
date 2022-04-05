import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular13Crud';

  isLoggedAdmin = false;
  isLoggedCustomer = false;
  adminName = '';
  customerName = '';

}