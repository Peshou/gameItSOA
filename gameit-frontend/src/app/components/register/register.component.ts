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
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private _userService: UserService,
              private _formBuilder: FormBuilder,
              private _authService: AuthService,
              private _navigationService: NavigationService
  ) { }

  ngOnInit() {
    if (this._userService.isLoggedIn()) {
      this._navigationService.goToHome();
    }

    this.initRegisterForm();
  }

  initRegisterForm() {
    this.registerForm = this._formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.compose([Validators.required,])]
    });
  }

  register() {
    if (this.registerForm.valid) {
      let formValue = this.registerForm.value;
      if (formValue["confirmPassword"] === formValue["password"]) {
        this._authService.signup(new User().deserialize(formValue))
          .subscribe(() => {
            this._navigationService.goToLogin();
          });
      }
    } else {
      FormValidators.markAllTouched(this.registerForm);
    }
  }

}
