import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {BaseService} from "./base.service";
import {User} from "../models/user.model";
import {Deserialize} from "cerialize";
import {Response, URLSearchParams} from "@angular/http";
import {UserService} from "./user.service";
import {Game} from "../models/game.model";
import {PaginatedResource} from "../models/paginanted-resource.model";

@Injectable()
export class PaymentService extends BaseService {

  constructor(private http: Http, private userService: UserService) {
    super(http);
  }

  createPaymentTransaction(game: Game, paymentToken: string) {
    const endpoint = "my-gateway/games/order";
    let params = {
      game: game,
      buyer: this.userService.getUserFromSession(),
      token: paymentToken
    };

    return this.post(endpoint, params).map((json: any) => {
      return true;
    }).catch((error: any) => {
      return Observable.throw(error || 'Server error');
    });
  }


}
