import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, UserService} from '../../_services';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators
} from '@angular/forms';
import {User} from '../../_models';

@Component({
  selector: 'app-edit-password',
  templateUrl: 'edit-password.component.html'
})
export class EditPasswordComponent implements OnInit {

  readonly userType!: string;
  userForm: FormGroup;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private userService: UserService) {

    if (this.route.snapshot.url[0].path === 'edit-any-password') {
      this.userType = 'any';
    } else if (this.route.snapshot.url[0].path === 'edit-my-password') {
      this.userType = 'my';
    }

    this.userForm = this.formBuilder.group({
      id: [''],
      version: [''],

      username: {value: '', disabled: true},
      newPassword: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64)]],
      newPasswordRepeated: ['']
    });

    const newPasswordControl = this.userForm.controls['newPassword'];
    const newPasswordRepeatedControl = this.userForm.controls['newPasswordRepeated'];

    newPasswordControl.valueChanges
    .subscribe(() => this.validatePasswordsMatch(newPasswordControl, newPasswordRepeatedControl));
    newPasswordRepeatedControl.valueChanges
    .subscribe(() => this.validatePasswordsMatch(newPasswordControl, newPasswordRepeatedControl));

    if (this.userType === 'my') {
      this.userForm.addControl('oldPassword', new FormControl('', Validators.required));
    }
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
    if (this.userType === 'my') {
      this.userService.getMyUser().subscribe((response: User) => {
        this.initializeForm(response);
      });
    } else if (this.userType === 'any') {
      if (!this.userService.userToEdit) {
        this.alert.error('editPassword.noUserLoaded');
        this.router.navigate(['/users']);
      } else {
        this.initializeForm(this.userService.userToEdit);
        this.userService.userToEdit = undefined;
      }
    }
  }

  private initializeForm(userToEdit: User) {
    this.userForm.controls['username'].setValue(userToEdit.username);
    this.userForm.controls['id'].setValue(userToEdit.id);
    this.userForm.controls['version'].setValue(userToEdit.version);
  }

  onSubmit() {
    if (this.userType === 'my') {
      this.userService.updateMyUserPassword(this.userForm.value, this.userForm.value)
      .subscribe(() => {
        this.alert.success('editPassword.success');
        this.router.navigate(['/home']);
      });
    } else if (this.userType === 'any') {
      this.userService.updateAnyUsersPassword(this.userForm.value, this.userForm.value)
      .subscribe(() => {
        this.alert.success('editPassword.success');
        this.router.navigate(['/users']);
      });
    }
  }

  onClickCancel() {
    if (this.userType === 'my') {
      this.router.navigate(['/home']);
    } else if (this.userType === 'any') {
      this.router.navigate(['/users']);
    }
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.userForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
