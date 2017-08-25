import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {FormValidators} from "../../util/forms/form_validators";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {Observable} from "rxjs/Observable";
import {OrderService} from "../../services/order.service";
import {PaginatedResource, PageDetails} from "../../models/paginanted-resource.model";
import {UserGameOrder} from "../../models/user-game-order.model";
import {PaginationInstance} from "ngx-pagination";

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


  isOrdersRequestSent: boolean = false;
  userOrdersPaginationConfig: PaginationInstance = {
    id: 'userOrdersPaginationConfig',
    itemsPerPage: UserGameOrder.PAGE_SIZE,
    currentPage: 0,
    totalItems: null
  };

  userOrdersList: PaginatedResource<UserGameOrder>;

  constructor(private _userService: UserService,
              private _formBuilder: FormBuilder,
              private _orderService: OrderService,
              private _authService: AuthService,
              private _navigationService: NavigationService) {
    this.user = this._userService.getUserFromSession();
  }

  ngOnInit(): void {
    this.getUserDetails();
    this.loadTabInfo();
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

  getUserOrders() {
    this.getAllOrders();
  }

  getAllOrders(pageNumber: number = 0) {
    if (!this.isOrdersRequestSent) {
      this.isOrdersRequestSent = true;
      this._orderService.getUserOrders(this.user.id, pageNumber, UserGameOrder.PAGE_SIZE)
        .subscribe((userOrdersList: PaginatedResource<UserGameOrder>) => {
          if (this.userOrdersList) {
            this.userOrdersList.append(userOrdersList);
          } else {
            this.userOrdersList = userOrdersList;
          }

          this.updatePaginationConfig(userOrdersList.page.number, userOrdersList.page.totalElements);
          this.isOrdersRequestSent = false;
        }, (error: any) => {
          this.isOrdersRequestSent = false;
        });
    }
  }

  private updatePaginationConfig(currentPage: number, totalCount: number) {
    this.userOrdersPaginationConfig.currentPage = currentPage;
    this.userOrdersPaginationConfig.totalItems = totalCount;
  }

  private calculateIndexForTable(index: number) {
    return this.userOrdersPaginationConfig.totalItems &&
      (this.userOrdersPaginationConfig.totalItems
      - ((index) + (this.userOrdersPaginationConfig.currentPage - 1)
      * this.userOrdersPaginationConfig.itemsPerPage));
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

  loadTabInfo() {
    switch (this.selectedTabIndex) {
      case UserDetailsTabs.userOrders:
        this.getUserOrders();
        break;

    }
  }
}
enum UserDetailsTabs {
  userDetails = 0,
  userOrders = 1
}

