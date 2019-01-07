import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AuthenticationService, SystemFeatureService} from '../../_services';
import {SystemFeature} from '../../_models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  private REGISTRATION_FEATURE_CODE = 'REGISTRATION';

  loginForm: FormGroup;
  registrationEnabled?: boolean;
  readonly path: string;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private auth: AuthenticationService,
              private systemFeatureService: SystemFeatureService) {

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.path = this.route.snapshot.queryParams['path'] || '/';
  }

  ngOnInit() {
    this.systemFeatureService
    .getSystemFeature(this.REGISTRATION_FEATURE_CODE)
    .subscribe((response: SystemFeature) => {
      this.registrationEnabled = response.enabled;
    });
  }

  onSubmit() {
    this.auth.authenticate(this.loginForm.value.username, this.loginForm.value.password, this.path);
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.loginForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
