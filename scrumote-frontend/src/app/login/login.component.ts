import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AuthenticationService} from '../_services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  loginForm: FormGroup;
  readonly path: string;

  constructor(
      private auth: AuthenticationService,
      private http: HttpClient,
      private router: Router,
      private route: ActivatedRoute,
      private formBuilder: FormBuilder) {

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.path = this.route.snapshot.queryParams['path'] || '/';
  }

  onSubmit() {
    this.auth.authenticate(this.loginForm.value.username, this.loginForm.value.password, this.path);
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.loginForm.controls[controlName].errors;
    if (errors) {
      return Object.keys(errors);
    }
  }
}
