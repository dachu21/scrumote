import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators
} from '@angular/forms';
import {AlertService, UserTokenService} from '../../_services';

@Component({
  selector: 'app-reset-password',
  templateUrl: 'reset-password.component.html'
})
export class ResetPasswordComponent implements OnInit {

  token!: string;
  passwordForm: FormGroup;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private userTokenService: UserTokenService) {

    this.passwordForm = this.formBuilder.group({
      username: {value: '', disabled: true},
      newPassword: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64)]],
      newPasswordRepeated: ['']
    });

    const newPasswordControl = this.passwordForm.controls['newPassword'];
    const newPasswordRepeatedControl = this.passwordForm.controls['newPasswordRepeated'];

    newPasswordControl.valueChanges
    .subscribe(() => this.validatePasswordsMatch(newPasswordControl, newPasswordRepeatedControl));
    newPasswordRepeatedControl.valueChanges
    .subscribe(() => this.validatePasswordsMatch(newPasswordControl, newPasswordRepeatedControl));
  }

  private validatePasswordsMatch(newPasswordControl: AbstractControl,
                                 newPasswordRepeatedControl: AbstractControl) {
    if (newPasswordControl.value !== newPasswordRepeatedControl.value) {
      newPasswordRepeatedControl.setErrors({mismatch: true});
    } else {
      newPasswordRepeatedControl.setErrors(null);
    }
  }

  ngOnInit() {
    const tokenParam = this.route.snapshot.paramMap.get('token');
    this.token = tokenParam ? tokenParam : '';

    this.userTokenService.getUsernameForToken(this.token).subscribe(response => {
      this.passwordForm.controls['username'].setValue(response.username);
    }, error1 => {
      this.router.navigate(['/login']);
    });
  }

  onSubmit() {
    this.userTokenService.resetUserPassword(this.token, this.passwordForm.value)
    .subscribe(() => {
      this.alert.success('editPassword.success');
      this.router.navigate(['/login']);
    });
  }

  onClickCancel() {
    this.router.navigate(['/login']);
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.passwordForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
