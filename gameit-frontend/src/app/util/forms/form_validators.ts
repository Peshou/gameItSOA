import {AbstractControl, FormArray, FormControl, FormGroup, ValidatorFn} from "@angular/forms";
export class FormValidators {
  static optionalEmailValidator(formControl: FormControl) {
    if (formControl.value && formControl.value.trim().length > 0) {
      let emailRegexTest = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return emailRegexTest.test(formControl.value) ? null : {'emailFormat': true};
    } else {
      return null;
    }
  }

  static optionalTimeValidator(formControl: FormControl) {
    if (formControl.value && formControl.value.trim().length > 0) {
      let timeRegexTest = /^([0-1][0-9]|2[0-3]):([0-5][0-9]) - ([0-1][0-9]|2[0-3]):([0-5][0-9])$/;
      return timeRegexTest.test(formControl.value) ? null : {'timeFormat': true};
    }
    else {
      return null;
    }
  }

  static optionalDateValidator(formControl: FormControl) {
    if (formControl.value && formControl.value.trim().length > 0) {
      let dateRegexTest = /^[0-9]{4}\/(1[0-2]|0[1-9])\/(3[01]|[12][0-9]|0[1-9])$/;
      return dateRegexTest.test(formControl.value) ? null : {'dateFormat': true};
    }
    else {
      return null;
    }
  }

  /**
   * This is NOT a generic method. It only applies to a form group with 3 form controls (year, month, day)
   * @param minimumAgeYears
   * @param formGroup
   * @return null | {any: Object} - Null if there are no errors, any if there are errors.
   */
  static minimumAgeValidator(minimumAgeYears, formGroup: FormGroup) {
    if (+formGroup.get('year').value && +formGroup.get('month').value && +formGroup.get('day').value) {
      let enteredYear = +formGroup.get('year').value;
      let enteredMonth = +formGroup.get('month').value - 1; //Month must be [0 - 11] (-1)
      let enteredDay = +formGroup.get('day').value;


      //Check if a user entered an invalid date.
      let dateEntered = new Date(enteredYear, enteredMonth, enteredDay);

      //The date is invalid if the values are changed after creation.
      if (dateEntered.getFullYear() != enteredYear
        || dateEntered.getMonth() != enteredMonth
        || dateEntered.getDate() != enteredDay) {
        return {invalidDate: true};
      }

      //Add minimum years to the date entered
      dateEntered.setFullYear(dateEntered.getFullYear() + minimumAgeYears);

      let today = new Date();

      //Date with minimum years is bigger than today. If it is, then we show an error
      if (today < dateEntered) {
        return {minimumAge: true};
      } else {
        return null;
      }
    } else {
      return {invalidDate: true};
    }
  }

  static atLeastOneCheckboxSelectedValidator(checkboxGroupArray: FormArray) {
    let atLeastOneSelected = checkboxGroupArray.controls.some((control: AbstractControl) => {
      return control.value === true;
    });

    if (!atLeastOneSelected) {
      return {"atLeastOneCheckboxSelected": true};
    }

    return null;
  }

  /**
   * Recursively mark the control and all of it's child controls as TOUCHED
   * @param control
   */
  static markAllTouched(control: AbstractControl) {
    if (control.hasOwnProperty('controls')) {
      control.markAsTouched(true); // mark group
      let ctrl = <any>control;
      for (let inner in ctrl.controls) {
        this.markAllTouched(ctrl.controls[inner] as AbstractControl);
      }
    }
    else {
      (<FormControl>(control)).markAsTouched(true);
    }
  }

  static inArray(arrayOfValues: any[]): ValidatorFn {
    return (control: FormControl): {[key: string]: any} => {
      if (arrayOfValues.indexOf(control.value)) {
        return null;
      }
      return {notInArray: true};
    };
  }

  static hasGroupInputErrors(inputGroup: string, inputField: string, formGroup: FormGroup) {
    return formGroup.get(inputGroup).errors && formGroup.get(inputGroup).touched
      || formGroup.get(inputGroup).get(inputField).errors && formGroup.get(inputGroup).get(inputField).touched;
  }

  static hasInputErrors(inputField: string, formGroup: FormGroup) {
    return formGroup.get(inputField).errors && formGroup.get(inputField).touched;
  }

  /**
   * Check if a form control has a particular error
   * @param inputField: string - The name of the Form Control
   * @param errorToCheck: string - The name of the Error
   * @param formGroup
   * @return {boolean} - True if the control has the error, false if it doesn't.
   */
  static hasParticularError(inputField: string, errorToCheck: string, formGroup: FormGroup) {
    return formGroup.get(inputField).hasError(errorToCheck) && formGroup.get(inputField).touched;
  }
}
