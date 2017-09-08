import {OnInit, Component} from "@angular/core";
import {GameService} from "../../services/game.service";
import {FormGroup, FormBuilder, Validators, AbstractControl, FormControl} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {Game} from "../../models/game.model";
import {UserService} from "../../services/user.service";
import {ToastrService} from "ngx-toastr";
import {NavigationService} from "../../services/navigation.service";
import {LoadingComponent} from "../../util/loading/loading.component";

@Component({
  selector: 'sell-game',
  templateUrl: './sell-game.component.html',
  styleUrls: ['./sell-game.component.scss'],
  providers: [GameService],
  viewProviders: [LoadingComponent]
})
export class SellGameComponent implements OnInit {

  sellNewGameForm: FormGroup;

  isCreateGameRequestSent: boolean = false;

  constructor(private _formBuilder: FormBuilder,
              private _userService: UserService,
              private _navigationService: NavigationService,
              private _toasterService: ToastrService,
              private _gamesService: GameService) {
    this.sellNewGameForm = _formBuilder.group({
      name: ['', Validators.required],
      gamePrice: [0, Validators.compose([CustomValidators.digits, Validators.required])],
      discountPercent: [0, Validators.compose([CustomValidators.digits, Validators.required])],
      releaseYear: [new Date().getFullYear(), Validators.compose([CustomValidators.rangeLength([3, 4]), Validators.required])],
      category: ['', Validators.required],
      description: ['', Validators.required],
      itemsLeft: [1, Validators.compose([CustomValidators.digits, CustomValidators.min(1), Validators.required])]
    });
  }

  get name() {
    return this.sellNewGameForm.get('name');
  }

  get gamePrice() {
    return this.sellNewGameForm.get('gamePrice');
  }

  get discountPercent() {
    return this.sellNewGameForm.get('discountPercent');
  }

  get releaseYear() {
    return this.sellNewGameForm.get('releaseYear');
  }

  get category() {
    return this.sellNewGameForm.get('category');
  }

  get description() {
    return this.sellNewGameForm.get('description');
  }

  get itemsLeft() {
    return this.sellNewGameForm.get('itemsLeft');
  }


  ngOnInit(): void {
  }

  sellNewGame() {
    if (this.sellNewGameForm.valid) {
      let newGame = new Game().deserialize(this.sellNewGameForm.value);
      newGame.userSeller = this._userService.getUserFromSession();

      this.isCreateGameRequestSent = true;
      this._gamesService.sellGame(newGame)
        .subscribe(() => {
          this._toasterService.success("The game has been added for sale.");
          this._navigationService.goToGameListScreen();
          this.isCreateGameRequestSent = false;
        }, (error: any) => {
          this._toasterService.error("An error occurred. Please try again.");
          this.isCreateGameRequestSent = false;
        });
    } else {
      this.markAllDirty(this.sellNewGameForm);
      this.markAllTouched(this.sellNewGameForm);
    }
  }

  /**
   * Recursively mark the control and all of it's child controls as TOUCHED
   * @param control
   */
  markAllTouched(control: AbstractControl) {
    if (control.hasOwnProperty('controls')) {
      control.markAsTouched(true); // mark group
      let ctrl = <any>control;
      for (let inner in ctrl.controls) {
        this.markAllTouched(ctrl.controls[inner] as AbstractControl);
      }
    }
    else {
      (<FormControl>(control)).markAsTouched(true);
    }
  }

  /**
   * Recursively mark the control and all of it's child controls as TOUCHED
   * @param control
   */
  markAllDirty(control: AbstractControl) {
    if (control.hasOwnProperty('controls')) {
      control.markAsDirty(true); // mark group
      let ctrl = <any>control;
      for (let inner in ctrl.controls) {
        this.markAllDirty(ctrl.controls[inner] as AbstractControl);
      }
    }
    else {
      (<FormControl>(control)).markAsDirty(true);
    }
  }
}
