import {Component, OnInit, Input} from '@angular/core';
import {PaginationInstance} from "ngx-pagination";
import {OrderService} from "../../../services/order.service";
import {User} from "../../../models/user.model";
import {UserGameOrder} from "../../../models/user-game-order.model";
import {PaginatedResource} from "../../../models/paginanted-resource.model";

@Component({
  selector: 'user-details-orders',
  templateUrl: './user-details-orders.component.html',
  styleUrls: ['./user-details-orders.component.scss']
})
export class UserDetailsOrdersComponent implements OnInit {
  @Input() user: User;

  isOrdersRequestSent: boolean = false;
  userOrdersPaginationConfig: PaginationInstance = {
    id: 'userOrdersPaginationConfig',
    itemsPerPage: UserGameOrder.PAGE_SIZE,
    currentPage: 0,
    totalItems: null
  };

  userOrdersList: PaginatedResource<UserGameOrder>;

  constructor(private _orderService: OrderService) {
  }

  ngOnInit(): void {
    this.getUserOrders();
  }

  getUserOrders() {
    this.getAllOrders();
  }

  getAllOrders(pageNumber: number = 1) {
    if (!this.isOrdersRequestSent) {
      this.isOrdersRequestSent = true;
      this._orderService.getUserOrders(this.user.id, pageNumber - 1, UserGameOrder.PAGE_SIZE)
        .subscribe((userOrdersList: PaginatedResource<UserGameOrder>) => {
          this.userOrdersList = userOrdersList;

          this.updatePaginationConfig(userOrdersList.page.number + 1, userOrdersList.page.totalElements);
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
}
