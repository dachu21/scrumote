import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, UserService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {User} from '../../_models';

@Component({
  selector: 'app-edit-user',
  templateUrl: 'edit-user.component.html'
})
export class EditUserComponent implements OnInit {

  readonly userType!: string;
  userForm: FormGroup;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private userService: UserService) {

    if (this.route.snapshot.url[0].path === 'edit-any-user') {
      this.userType = 'any';
    } else if (this.route.snapshot.url[0].path === 'edit-my-user') {
      this.userType = 'my';
    }

    this.userForm = this.formBuilder.group({
      id: [''],
      version: [''],

      username: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(32)]],
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

  ngOnInit() {
    if (this.userType === 'my') {
      this.userService.getMyUser().subscribe((response: User) => {
        this.initializeForm(response);
      });
    } else if (this.userType === 'any') {
      if (!this.userService.userToEdit) {
        this.alert.error('editUser.noUserLoaded');
        this.router.navigate(['/users']);
      } else {
        this.initializeForm(this.userService.userToEdit);
        this.userService.userToEdit = undefined;
      }
    }
  }

  private initializeForm(userToEdit: User) {
    this.userForm.controls['username'].setValue(userToEdit.username);
    this.userForm.controls['email'].setValue(userToEdit.email);
    this.userForm.controls['firstName'].setValue(userToEdit.firstName);
    this.userForm.controls['lastName'].setValue(userToEdit.lastName);

    this.userForm.controls['id'].setValue(userToEdit.id);
    this.userForm.controls['version'].setValue(userToEdit.version);
  }

  onSubmit() {
    if (this.userType === 'my') {
      this.userService.updateMyUser(this.userForm.value)
      .subscribe(() => {
        this.alert.success('editUser.success');
        this.router.navigate(['/home']);
      });
    } else if (this.userType === 'any') {
      this.userService.updateAnyUser(this.userForm.value)
      .subscribe(() => {
        this.alert.success('editUser.success');
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
