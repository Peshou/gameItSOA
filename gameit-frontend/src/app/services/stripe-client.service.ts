import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment";
import {UserService} from "./user.service";

declare var StripeCheckout:any;

@Injectable()
export class StripeClientService {

  constructor(private _userService: UserService) {
  }
  //https://stripe.com/docs/checkout#integration-custom

  checkout(price: number):Observable<any> {

    this._solveCircularReference();

    return Observable.create(observer => {

      var handler = StripeCheckout.configure({
        key: environment.stripeKey,
        image: '/assets/logoSmall.png',
        color: 'black',
        locale: 'auto',
        token: (token, args) => {
          // Use the token to create the charge with a server-side script.
          // You can access the token ID with `token.id`
          observer.next(token);
          observer.complete();
        }
      });

      handler.open({
        name: 'GameIt Store Game purchase',
        description: 'Purchase game',
        amount: Number(price * 100),
        currency: 'USD',
        email: this._userService.getUserFromSession().email
      });

      window.addEventListener('popstate', function() {
        handler.close();
      });
    });
  }

  private _solveCircularReference(){
    //Dirty fix for Stripe checkout.js problem with a circular reference on Angular 2

    const _stringify = <any> JSON.stringify;
    JSON.stringify = function (value, ...args) {
      if (args.length) {
        return _stringify(value, ...args);
      } else {
        return _stringify(value, function (key, value) {
          if (value && key === 'zone' && value['_zoneDelegate']
            && value['_zoneDelegate']['zone'] === value) {
            return undefined;
          }
          return value;
        });
      }
    };
  }
}
