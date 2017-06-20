import {Component, Input} from '@angular/core';

@Component({
  selector: 'loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {
  @Input() useFontAwesomeSpinner: boolean = true;
  @Input() useBiggerFontAwesomeSpinner: boolean = true;
  @Input() whiteBackground: boolean = true;
  @Input() coverWholeScreen: boolean = false;
}
