import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {AlertService, TranslationsService, UserService} from '../_services';

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html'
})
export class RegisterComponent {

  labels: any;
  errorMessageResources: any;

  registerForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private userService: UserService,
      private alertService: AlertService,
      private translations: TranslationsService) {

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: [''],
      lastName: ['']
    });

    translations.labels.subscribe(value => this.labels = value);
    translations.errorMessageResources.subscribe(value => this.errorMessageResources = value);
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
