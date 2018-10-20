import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AlertService, UserService} from '../_services';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html'
})
export class RegisterComponent {

  registerForm: FormGroup;
  loading = false;
  submitted = false;

  errorMessageResources = undefined;

  labels = {
    username: 'Username',
    password: 'Password',
    email: 'E-mail',
    firstName: 'First name',
    lastName: 'Last name'
  };

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private userService: UserService,
      private alertService: AlertService,
      private translateService: TranslateService) {

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: [''],
      lastName: ['']
    });

    this.translateService.get("errorMessageResources").subscribe((res: any) => {
      this.errorMessageResources = res;
      console.log(this.errorMessageResources);
    });
  }

  get form() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.userService.register(this.registerForm.value)
    .pipe(first())
    .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
