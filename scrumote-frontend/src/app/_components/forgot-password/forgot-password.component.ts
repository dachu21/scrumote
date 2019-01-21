import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AlertService, UserTokenService} from '../../_services';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html'
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: FormGroup;
  readonly path: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private alert: AlertService,
              private userTokenService: UserTokenService) {

    this.forgotPasswordForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [
        Validators.required,
        Validators.email]]
    });

    this.path = this.route.snapshot.queryParams['path'] || '/';
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userTokenService.createResetPasswordToken(this.forgotPasswordForm.value).subscribe(() => {
      this.alert.success('forgotPassword.success');
      this.router.navigate(['/login']);
    });
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.forgotPasswordForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
