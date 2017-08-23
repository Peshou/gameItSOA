import {OnInit, Component} from "@angular/core";
import {GameService} from "../../services/game.service";
import {Game} from "../../models/game.model";
import {ActivatedRoute} from "@angular/router";
import {NavigationService} from "../../services/navigation.service";
import {FileUploader} from "ng2-file-upload";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'edit-game',
  templateUrl: 'edit-game.component.html',
  styleUrls: ['edit-game.component.scss'],
  providers: [GameService]
})
export class EditGameComponent implements OnInit {
  isGameRequestSent: boolean = false;
  isGameEditInProgress: boolean = false;
  game: Game;

  public uploader: FileUploader = new FileUploader({
    url: environment.imageUploadUrl,
    removeAfterUpload: true
  });
  public hasBaseDropZoneOver: boolean = false;

  constructor(private _activatedRoute: ActivatedRoute,
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
        this.uploader.options.url = `${environment.imageUploadUrl}/games/${this.game.id}/upload`;
        this.uploader.options.method = "POST";
        this.uploader.authToken = 'Bearer ' + sessionStorage.getItem('access-token');
        this.initFileUploadCompleteCallback();

        this.isGameRequestSent = false;
      }, (error: any) => {
        this.isGameRequestSent = false;
      });
  }

  initFileUploadCompleteCallback() {
    this.uploader.onSuccessItem = () => {
      this._gameService.getGame(this.game.id).subscribe((game: Game) => {
        this.game = game;
      });
    };

    console.log(this.uploader);
  }

  saveEditGameChanges() {

  }

  cancelEditingGame() {
    this._navigationService.goToGameDetailsPage(this.game.id);
  }

  public fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }
}
