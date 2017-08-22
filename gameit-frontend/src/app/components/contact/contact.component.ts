import {Component} from '@angular/core';
import {ContactInfo} from "../../models/contact-info.model";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
  contact: ContactInfo;

  constructor(private _toasterService: ToastrService) {
    this.contact = new ContactInfo();
  }


  sendContactMessage() {
    this._toasterService.success("The contact message has been sent", "Contact Message");
  }
}
