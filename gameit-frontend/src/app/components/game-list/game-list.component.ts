import {Component, OnInit} from '@angular/core';
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {GameService} from "../../services/game.service";
import {Game} from "../../models/game.model";
import {PaginatedResource} from "../../models/paginanted-resource.model";
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {LoadingComponent} from "../../util/loading/loading.component";

@Component({
  selector: 'game-list',
  templateUrl: './game-list.component.html',
  providers: [GameService],
  viewProviders: [LoadingComponent],
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {
  gameList: PaginatedResource<Game>;
  isGameRequestSent: boolean = false;

  showAsGrid = true;

  constructor(private _userService: UserService,
              private _gameService: GameService,
              private _shoppingCartService: ShoppingCartService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    this.getAllGames();
  }

  getAllGames(pageNumber: number = 0) {
    if (!this.isGameRequestSent) {
      this.isGameRequestSent = true;
      this._gameService.getAllGames(pageNumber, Game.PAGE_SIZE).subscribe((gameList: PaginatedResource<Game>) => {
        if (this.gameList) {
          this.gameList.append(gameList);
        } else {
          this.gameList = gameList;
        }

        console.log(gameList);
        this.isGameRequestSent = false;
      }, (error: any) => {
        this.isGameRequestSent = false;
      });
    }
  }

  showGrid(showAsGrid: boolean) {
    this.showAsGrid = showAsGrid;
  }

  addItemToCard(game: Game) {
    this._shoppingCartService.addItem(game);
  }

  buyItem(game: Game) {
    this._navigationService.goToGameDetailsPage(game.id);
  }

  onGamesListScroll() {
    if (this.gameList.page.number < this.gameList.page.totalPages) {
      this.getAllGames(this.gameList.page.number + 1);
    }
  }


}
