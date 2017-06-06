import {AfterViewInit, Component, OnInit, ViewChild, OnDestroy} from "@angular/core";
import {UserService} from "../../services/user.service";
import {NavigationService} from "../../services/navigation.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../services/auth.service";
@Component({
  selector: "home",
  templateUrl: "home.component.html",
  styleUrls: ['home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  loginStatusSubscription: Subscription;

  isUserLoggedIn: boolean = false;

  constructor(private _authService: AuthService,
              private _userService: UserService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    this.subscribeToLoginNotifications();
  }

  subscribeToLoginNotifications() {
    this.loginStatusSubscription = this._userService.isLoggedIn$
      .subscribe((isLoggedIn: boolean) => {
        console.log(isLoggedIn);
        this.isUserLoggedIn = isLoggedIn;
      });
  }

  goToLogin() {
    this._navigationService.goToLogin();
  }

  goToRegister() {
    this._navigationService.goToRegister();
  }

  logout() {
    this._authService.logOut(() => {
      this._userService.onLogoutUser();
      this.isUserLoggedIn = false;
    });
  }

  unsubscribe(subscription: Subscription) {
    if (subscription) subscription.unsubscribe();
  }

  ngOnDestroy() {
    this.unsubscribe(this.loginStatusSubscription);
  }
}
