import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {Observable} from "rxjs/Observable";
import {OrderService} from "../../services/order.service";

@Component({
  selector: 'user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
  providers: [OrderService]
})
export class UserDetailsComponent implements OnInit {
  user: User;
  editUserCopy: User;

  isUserRequestSent: boolean = true;
  userEditMode: boolean = false;

  selectedTabIndex = 0;

  constructor(private _userService: UserService,
              private _formBuilder: FormBuilder,
              private _authService: AuthService,
              private _navigationService: NavigationService) {
    this.user = this._userService.getUserFromSession();
  }

  ngOnInit(): void {
    this.getUserDetails();
  }

  private getUserDetails() {
    this.isUserRequestSent = true;
    this.getUser().subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
      this.isUserRequestSent = false;
    }, (error: any) => {
      this.isUserRequestSent = false;
    });
  }

  getUser() {
    if (this._userService.getUserFromSession().id) {
      return Observable.of(this._userService.getUserFromSession());
    } else {
      return this._authService.getUserDetails();
    }
  }

  editUser() {
    this.editUserCopy = this.user.produceDeepCopy();
    this.userEditMode = true;
  }

  saveEditUserChanges() {
    this._userService.editUser(this.editUserCopy)
      .subscribe((user: User) => {
        this.user = user;
        this.cancelEditUserChanges();
      });
  }

  cancelEditUserChanges() {
    this.userEditMode = false;
    this.editUserCopy = null;
  }

  userDetailsTabActive() {
    return this.selectedTabIndex == UserDetailsTabs.userDetails;
  }

  userOrdersTabActive() {
    return this.selectedTabIndex == UserDetailsTabs.userOrders;
  }
}
enum UserDetailsTabs {
  userDetails = 0,
  userOrders = 1
}

