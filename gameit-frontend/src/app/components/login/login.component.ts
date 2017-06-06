import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {UserService} from "../../services/user.service";
import {NavigationService} from "../../services/navigation.service";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  providers: [],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;

  constructor(private _router: Router,
              private _authService: AuthService,
              private _userService: UserService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    if (this._userService.isLoggedIn()) {
      this._navigationService.goToHome();
    }
  }

  login() {
    this._authService.fullLogin(this.email, this.password)
      .subscribe((user: User) => {
        console.log(user);
        this._navigationService.goToHome();
      });

    // this._router.navigate(['/']);
  }

}
