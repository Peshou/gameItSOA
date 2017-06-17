import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {CustomValidators} from "ng2-validation";
import {FormValidators} from "../../util/forms/form_validators";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {GameService} from "../../services/game.service";
import {Game} from "../../models/game.model";
import {PaginatedResource} from "../../models/paginanted-resource.model";

@Component({
  selector: 'game-list',
  templateUrl: './game-list.component.html',
  providers: [GameService],
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {
  gameList: PaginatedResource<Game>;

  isGameRequestSent: boolean = false;

  constructor(private _userService: UserService,
              private _gameService: GameService,
              private _navigationService: NavigationService) {
  }

  ngOnInit() {
    this.getAllGames();

  }

  getAllGames() {
    if (!this.isGameRequestSent) {
      this.isGameRequestSent = true;
      this._gameService.getAllGames().subscribe((gameList: PaginatedResource<Game>) => {
        this.gameList = gameList;
        console.log(gameList);
      });
    }

  }


}
