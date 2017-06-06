import { Component } from '@angular/core';
import {NavigationService} from "./services/navigation.service";
import {UserService} from "./services/user.service";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [AuthService, UserService, NavigationService],
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';
}
