import {Component, OnInit} from "@angular/core";
import {GameService} from "../../../services/game.service";
import {StripeClientService} from "../../../services/stripe-client.service";
import {OrderService} from "../../../services/order.service";
import {LoadingComponent} from "../../../util/loading/loading.component";
import {ActivatedRoute} from "@angular/router";
import {Game} from "../../../models/game.model";
import {ShoppingCartService} from "../../../services/shopping-cart.service";
import {ToastrService} from "ngx-toastr";
import {UserService} from "../../../services/user.service";
import {NavigationService} from "../../../services/navigation.service";

@Component({
  selector: 'game-details',
  templateUrl: 'game-details.component.html',
  providers: [GameService, StripeClientService, OrderService],
  viewProviders: [LoadingComponent],
  styleUrls: ['game-details.component.scss']
})
export class GameDetailsComponent implements OnInit {
  isGameRequestSent: boolean = false;
  isPaymentInProgress: boolean = false;
  game: Game;

  constructor(private _activatedRoute: ActivatedRoute,
              private _stripeClient: StripeClientService,
              private _paymentService: OrderService,
              public shoppingCartService: ShoppingCartService,
              private _toaster: ToastrService,
              private _userService: UserService,
              private _navigationService: NavigationService,
              private _gameService: GameService) {
  }

  ngOnInit(): void {
    this._getGameFromUrlParam();
  }

  private _getGameFromUrlParam() {
    this.isGameRequestSent = true;
    this._activatedRoute.params
      .map(params => params['id'])
      .switchMap(id => this._gameService.getGame(id))
      .subscribe((game: Game) => {
        this.game = game;
        console.log(Number(this.game.getPriceWithDiscount()));
        this.isGameRequestSent = false;
      }, (error: any) => {
        this.isGameRequestSent = false;
      });
  }

  buyGame() {
    this._stripeClient.checkout(this.game.getPriceWithDiscount()).subscribe(
      (token: any) => {
        this.isPaymentInProgress = true;
        this._paymentService.createPaymentTransaction(this.game, token.id)
          .subscribe(() => {
            this.isPaymentInProgress = false;
            this._toaster.success("Transaction successful. You will receive a completion email soon.", "Successful payment");
          }, (error: string) => {
            this.isPaymentInProgress = false;
            this._toaster.error("An error occurred, please try again.", "Payment error");
          });
      });
  }

  addGameToShoppingCart() {
    this.shoppingCartService.addItem(this.game);
  }

  isCurrentUserTheGameSeller() {
    let user = this._userService.getUserFromSession();
    return (user && this.game.userSeller) ? user.id == this.game.userSeller.id : false;
  }

  goToEditGame() {
    this._navigationService.goToEditGamePage(this.game.id);
  }

}
