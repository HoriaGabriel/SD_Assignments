import { Component } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular13Crud';

  private role: string = '';
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    console.log("here");

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user.roles;
      console.log("here2");
      if(this.role=="ROLE_ADMIN"){
        console.log("here3");
        this.showAdminBoard = true;
      }else{
        console.log("here4");
        console.log(this.role);
      }

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
