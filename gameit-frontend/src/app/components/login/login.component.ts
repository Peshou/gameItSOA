import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
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
  registeredMessage: string;

  email: string;
  password: string;

  constructor(private _authService: AuthService,
              private _userService: UserService,
              private _activatedRoute: ActivatedRoute,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    if (this._userService.isLoggedIn()) {
      this._navigationService.goToHome();
    }

    this.checkIfUserCameFromRegister();
  }

  login() {
    this._authService.fullLogin(this.email, this.password)
      .subscribe((user: User) => {
        console.log(user);
        this._navigationService.goToHome();
      });
  }

  checkIfUserCameFromRegister() {
    console.log(this._activatedRoute);
    this._activatedRoute
      .params
      .subscribe(params => {
        console.log(params);
        if (params['justRegistered']) {
          this.registeredMessage = "Your account has been created. You can now log in.";
        }
      });
  }

}
