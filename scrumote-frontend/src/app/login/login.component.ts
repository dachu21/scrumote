import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AlertService, AuthenticationService} from '../_services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  loginForm: FormGroup;
  loading = false;
  submitted = false;

  returnUrl: string;

  constructor(
      private authenticationService: AuthenticationService,
      private http: HttpClient,
      private router: Router,
      private route: ActivatedRoute,
      private alertService: AlertService,
      private formBuilder: FormBuilder) {

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.authenticationService.authenticate(this.loginForm.value.username, this.loginForm.value.password, () => {
      this.router.navigateByUrl(this.returnUrl);
    });
    return false;

    // this.authenticationService.login(this.f.username.value, this.f.password.value)
    // .pipe(first())
    // .subscribe(
    //     data => {
    //       this.router.navigate([this.returnUrl]);
    //     },
    //     error => {
    //       this.alertService.error(error);
    //       this.loading = false;
    //     });
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.loginForm.controls[controlName].errors;
    if (errors) {
      return Object.keys(errors);
    }
  }
}
