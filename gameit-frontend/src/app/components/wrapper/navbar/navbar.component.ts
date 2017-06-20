import {Component, OnInit, OnDestroy} from "@angular/core";
import {Subscription} from "rxjs";
import {User} from "../../../models/user.model";
import {UserService} from "../../../services/user.service";
import {AuthService} from "../../../services/auth.service";
import {NavigationService, RoutesPaths} from "../../../services/navigation.service";
import {isNullOrUndefined} from "util";

@Component({
  selector: 'navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  isUserLoggedIn: boolean;
  shouldShowNavbar: boolean = true;

  private user: User;
  private _isLoggedInSubscription: Subscription;
  private _userSourceSubscription: Subscription;
  private _currentPathSubscription: Subscription;

  constructor(private _authService: AuthService,
              private _userService: UserService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    this.isUserLoggedIn = this._userService.isLoggedIn();

    this._isLoggedInSubscription = this._userService.isLoggedIn$
      .subscribe(isLoggedIn => {
        this.isUserLoggedIn = isLoggedIn;
      });

    this._userSourceSubscription = this._userService.userSource$
      .subscribe((user => {
        this.user = user;
      }));

    this._currentPathSubscription = this._navigationService.currentPath$().subscribe(path => {
      this.shouldShowNavbar = (path !== RoutesPaths.home);
    });
  }

  ngOnDestroy() {
    if (this._isLoggedInSubscription) {
      this._isLoggedInSubscription.unsubscribe();
    }
    this._userSourceSubscription.unsubscribe();
  }

  logout() {
    this._authService.logOut(() => {
      this._userService.onLogoutUser();
      this.isUserLoggedIn = false;
    });
  }

  goToGameListScreen() {
    this._navigationService.goToGameListScreen();
  }

  goToHomeScreen() {
    this._navigationService.goToHome();
  }

  goToProfileScreen() {

  }

  goToRegisterScreen() {
    this._navigationService.goToRegister();
  }

  goToLoginScreen() {
    this._navigationService.goToLogin();
  }

  getProfileTabTitle() {
    return !isNullOrUndefined(this.user) ? this.user.username : "PROFILE"
  }

  goToContactUs() {
    this._navigationService.goToContactUs();
  }
}
