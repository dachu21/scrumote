import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService, AuthenticationService, TranslationsService} from "../_services";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  labels: any;
  errorMessageResources: any;

  loginForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;

  returnUrl: string;

  constructor(
      private authenticationService: AuthenticationService,
      private http: HttpClient,
      private router: Router,
      private route: ActivatedRoute,
      private alertService: AlertService,
      private formBuilder: FormBuilder,
      private translations: TranslationsService) {

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    translations.labels.subscribe(value => this.labels = value);
    translations.errorMessageResources.subscribe(value => this.errorMessageResources = value);

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

}