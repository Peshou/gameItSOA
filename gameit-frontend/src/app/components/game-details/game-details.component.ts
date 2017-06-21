import {Component, OnInit} from "@angular/core";
import {GameService} from "../../services/game.service";
import {LoadingComponent} from "../../util/loading/loading.component";
import {ActivatedRoute} from "@angular/router";
import {Game} from "../../models/game.model";
import {StripeClientService} from "../../services/stripe-client.service";
import {PaymentService} from "../../services/payment.service";

@Component({
  selector: 'game-details',
  templateUrl: './game-details.component.html',
  providers: [GameService, StripeClientService, PaymentService],
  viewProviders: [LoadingComponent],
  styleUrls: ['./game-details.component.scss']
})
export class GameDetailsComponent implements OnInit {
  isGameRequestSent: boolean = false;
  isPaymentInProgress: boolean = false;
  game: Game;

  constructor(private _activatedRoute: ActivatedRoute,
              private _stripeClient: StripeClientService,
              private _paymentService: PaymentService,
              private _gameService: GameService) {
  }

  ngOnInit(): void {
    this.getGameFromUrlParam();
  }

  private getGameFromUrlParam() {
    this.isGameRequestSent = true;
    this._activatedRoute.params
      .map(params => params['id'])
      .switchMap(id => this._gameService.getGame(id))
      .subscribe((game: Game) => {
        this.game = game;
        console.log(this.game.getPriceWithDiscount());
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
          }, (error: string) => {
            this.isPaymentInProgress = false;
          });
      });
  }

}
