import {OnInit, Component} from "@angular/core";
import {GameService} from "../../services/game.service";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'sell-game',
  templateUrl: 'sell-game.component.html',
  styleUrls: ['sell-game.component.scss']
})
export class SellGameComponent implements OnInit {

  sellNewGameForm: FormGroup;

  constructor(private _formBuilder: FormBuilder, private _gamesService: GameService) {
    this.sellNewGameForm = _formBuilder.group({
      name: ['', Validators.required],
      price: [0, Validators.compose([CustomValidators.digits, Validators.required])],
      discountPercent: [0, Validators.compose([CustomValidators.digits, Validators.required])],
      releaseYear: [new Date().getFullYear(), Validators.compose([CustomValidators.rangeLength([4, 4]), Validators.required])],
      category: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

}
