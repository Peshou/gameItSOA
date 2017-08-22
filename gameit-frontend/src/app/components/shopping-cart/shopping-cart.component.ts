import {Component, OnInit} from "@angular/core";
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {Game} from "../../models/game.model";
import {NavigationService} from "../../services/navigation.service";
import {PaymentService} from "../../services/payment.service";
import {StripeClientService} from "../../services/stripe-client.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'shopping-cart',
  templateUrl: 'shopping-cart.component.html',
  styleUrls: ['shopping-cart.component.scss'],
  providers: [PaymentService, StripeClientService]
})
export class ShoppingCartComponent implements OnInit {
  shoppingCartItems: Game[] = [];
  isPaymentInProgress: boolean = false;

  constructor(public shoppingCartService: ShoppingCartService,
              private _stripeClient: StripeClientService,
              private _paymentService: PaymentService,
              private _toaster: ToastrService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    this.shoppingCartItems = this.shoppingCartService.getCartItems();
    console.log(this.shoppingCartItems);
  }

  removeItemFromCart(game: Game) {
    this.shoppingCartItems = this.shoppingCartService.removeItem(game);
  }

  calculateTotalPrice(): number {
    return this.shoppingCartItems.reduce((priceSum: number, currentGame: Game) => {
      return priceSum + currentGame.getPriceWithDiscount() * currentGame.quantity;
    }, 0);
  }

  proceedToCheckout() {
    if (this.shoppingCartItems.length) {
      this.purchaseGame(this.shoppingCartItems[0]);
    }
  }

  purchaseGame(game: Game) {
    this._stripeClient.checkout(game.getPriceWithDiscount()).subscribe(
      (token: any) => {
        this.isPaymentInProgress = true;
        this._paymentService.createPaymentTransaction(game, token.id)
          .subscribe(() => {
            this.isPaymentInProgress = false;
            this.shoppingCartItems = this.shoppingCartService.removeItem(game, true);
            if (this.shoppingCartItems.length) {
              this.purchaseGame(this.shoppingCartItems[0]);
            } else {
              this._toaster.success("The transactions are successful. You will receive completion emails soon.", "Successful payment");
            }
          }, (error: string) => {
            this.isPaymentInProgress = false;
            this._toaster.error("An error occurred, please try again.", "Payment error");
          });
      });
  }

  goToGamesList() {
    this._navigationService.goToGameListScreen();
  }
}
