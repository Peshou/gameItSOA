import {Component, Input} from '@angular/core';

@Component({
  selector: 'loading',
  templateUrl: './loading.component.html',
})
export class LoadingComponent {
  @Input() useFontAwesomeSpinner: boolean = false;
  @Input() useBiggerFontAwesomeSpinner: boolean = false;
  @Input() whiteBackground: boolean = false;
  @Input() coverWholeScreen: boolean = true;
}
