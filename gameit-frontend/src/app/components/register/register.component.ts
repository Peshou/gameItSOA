import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NavigationService} from "../../services/navigation.service";
import {UserService} from "../../services/user.service";
import {FormValidators} from "../../util/forms/form_validators";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  registerError: string = "";

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
    let password = new FormControl('', Validators.compose([Validators.required, Validators.minLength(8)]));
    let certainPassword = new FormControl('', Validators.compose([Validators.required, CustomValidators.equalTo(password)]));

    this.registerForm = this._formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: password,
      confirmPassword: certainPassword,
      sellerAccount: [false, Validators.required]
    });
  }

  register() {
    if (this.registerForm.valid) {
      let formValue = this.registerForm.value;
      if (formValue["confirmPassword"] === formValue["password"]) {
        this._authService.signup(new User().deserialize(formValue), formValue.sellerAccount)
          .subscribe(() => {
            this._navigationService.goToLogin(true);
          }, (error: any) => {
            this.registerError = error.json().message || "An error occurred. Please try again.";
          });
      }
    } else {
      FormValidators.markAllTouched(this.registerForm);
    }
  }

  hasInputErrors(inputField: string) {
    return FormValidators.hasInputErrors(inputField, this.registerForm);
  }

  /**
   * Check if a form control has a particular error
   * @param inputField: string - The name of the Form Control
   * @param errorToCheck: string - The name of the Error
   * @return {boolean} - True if the control has the error, false if it doesn't.
   */
  hasParticularError(inputField: string, errorToCheck: string) {
    return FormValidators.hasParticularError(inputField, errorToCheck, this.registerForm);
  }
}

