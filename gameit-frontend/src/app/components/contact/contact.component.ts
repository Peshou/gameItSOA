import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {CustomValidators} from "ng2-validation";
import {FormValidators} from "../../util/forms/form_validators";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent   {

}
