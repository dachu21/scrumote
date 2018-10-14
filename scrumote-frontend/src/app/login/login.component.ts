import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService, AuthenticationService} from "../_services";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  get form() {
    return this.loginForm.controls;
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  constructor(
      private authenticationService: AuthenticationService,
      private http: HttpClient,
      private router: Router,
      private route: ActivatedRoute,
      private alertService: AlertService,
      private formBuilder: FormBuilder
  ) {
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.authenticationService.authenticate(this.form.username.value, this.form.password.value, () => {
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