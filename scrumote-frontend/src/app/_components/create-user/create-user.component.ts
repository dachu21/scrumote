import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AlertService, UserService} from '../../_services';

@Component({
  selector: 'app-create-user',
  templateUrl: 'create-user.component.html'
})
export class CreateUserComponent {

  readonly newUserType?: string;
  newUserForm: FormGroup;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private alert: AlertService,
              private userService: UserService) {

    if (this.route.snapshot.url[0].path === 'register') {
      this.newUserType = 'register';
    } else if (this.route.snapshot.url[0].path === 'create-user') {
      this.newUserType = 'create';
    }

    this.newUserForm = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(32)]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64)]],
      email: ['', [
        Validators.required,
        Validators.email,
        Validators.maxLength(64)]],
      firstName: ['',
        Validators.maxLength(32)],
      lastName: ['',
        Validators.maxLength(32)]
    });
  }

  onSubmit() {
    if (this.newUserType === 'register') {
      this.userService.registerUser(this.newUserForm.value)
      .subscribe(() => {
        this.alert.success('createUser.register.success');
        this.router.navigate(['/login']);
      });
    } else if (this.newUserType === 'create') {
      this.userService.createUser(this.newUserForm.value)
      .subscribe(() => {
        this.alert.success('createUser.create.success');
        this.router.navigate(['/users']);
      });
    }
  }

  onClickCancel() {
    if (this.newUserType === 'register') {
      this.router.navigate(['/login']);
    } else if (this.newUserType === 'create') {
      this.router.navigate(['/users']);
    }
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.newUserForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
