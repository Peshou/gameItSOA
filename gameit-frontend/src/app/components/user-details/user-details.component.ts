import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {FormValidators} from "../../util/forms/form_validators";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {CustomValidators} from "ng2-validation";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {
  user: User;
  isUserRequestSent: boolean = true;

  constructor(private _activatedRoute: ActivatedRoute,
              private _userService: UserService,
              private _formBuilder: FormBuilder,
              private _authService: AuthService,
              private _navigationService: NavigationService) {
    this.user = this._userService.getUserFromSession();
  }

  ngOnInit(): void {
    this.getUserFromUrlParam();
  }

  private getUserFromUrlParam() {
    this.isUserRequestSent = true;
    this._activatedRoute.params
      .map(params => params['id'])
      .switchMap(id => this.getUser(id))
      .subscribe((user: User) => {
        this.user = user;
        console.log(this.user);
        this.isUserRequestSent = false;
      }, (error: any) => {
        this.isUserRequestSent = false;
      });
  }

  getUser(userId: string){
    if(this._userService.getUserFromSession().id == userId) {
      return Observable.of(this._userService.getUserFromSession());
    } else {
      return this._userService.getUser(userId);
    }
  }
}

