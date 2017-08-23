import {OnInit, Component} from "@angular/core";
import {GameService} from "../../services/game.service";

@Component({
  selector: 'sell-game',
  templateUrl: 'sell-game.component.html',
  styleUrls: ['sell-game.component.scss']
})
export class SellGameComponent implements OnInit {
  constructor(private _gamesService: GameService) {
  }

  ngOnInit(): void {
  }

}
