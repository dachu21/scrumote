import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {AlertService, UserService} from '../../_services';

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private userService: UserService,
      private alert: AlertService) {

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: [''],
      lastName: ['']
    });
  }

  onSubmit() {
    this.userService.register(this.registerForm.value)
    .pipe(first())
    .subscribe(data => {
      this.alert.success('register.success');
      this.router.navigate(['/login']);
    });
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.registerForm.controls[controlName].errors;
    if (errors) {
      return Object.keys(errors);
    }
  }
}
