import {Attribute, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  selector: '[appPasswordValidator]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => PasswordValidator), multi: true}
  ]
})

export class PasswordValidator implements Validator {
  constructor(
      @Attribute('appPasswordValidator')
      public conditions: boolean
  ) {
  }

  validate(formControl: AbstractControl): { [key: string]: any } {
    const password = formControl.value;

    const uppercaseRegex = new RegExp('(?=.*[A-Z])');
    const hasUppercase = uppercaseRegex.test(password);

    const numberRegex = new RegExp('(?=.*\\d)');
    const hasNumber = numberRegex.test(password);

    if (!hasNumber || !hasUppercase) {
      return {conditions: true};
    } else {
      return {};
    }
  }
}
