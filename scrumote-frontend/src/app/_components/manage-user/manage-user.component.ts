import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ValidationErrors} from '@angular/forms';
import {AlertService, RoleService, UserService} from '../../_services';
import {Role, User} from '../../_models';

@Component({
  selector: 'app-manage-user',
  templateUrl: 'manage-user.component.html'
})
export class ManageUserComponent implements OnInit {

  userForm: FormGroup;

  allRoles = new Map<string, number>();
  allRolesKeys?: string[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private roleService: RoleService) {

    this.userForm = this.formBuilder.group({
      id: [''],
      version: [''],

      username: {value: '', disabled: true},
      active: [''],
      roles: [''],

      roleNames: ['']
    });
  }

  ngOnInit() {
    if (!this.userService.userToEdit) {
      this.alert.error('manageUser.noUserLoaded');
      this.router.navigate(['/users']);
    } else {
      this.initializeForm(this.userService.userToEdit);
      this.userService.userToEdit = undefined;
    }

    this.roleService.getAllRoles().subscribe((response: Role[]) => {
      this.allRoles = response.reduce(function (roleMap, role) {
        if (role.id) {
          roleMap.set(role.name, role.id);
        }
        return roleMap;
      }, this.allRoles);
      this.allRolesKeys = Array.from(this.allRoles.keys());
    });
  }

  private initializeForm(userToEdit: User) {
    this.userForm.controls['username'].setValue(userToEdit.username);
    this.userForm.controls['active'].setValue(userToEdit.active);
    this.userForm.controls['roleNames'].setValue(userToEdit.roleNames);
    this.userForm.controls['id'].setValue(userToEdit.id);
    this.userForm.controls['version'].setValue(userToEdit.version);
  }

  onSubmit() {
    const selectedUsernames = <string[]>this.userForm.controls['roleNames'].value;
    if (selectedUsernames) {
      this.userForm.controls['roles']
      .setValue(selectedUsernames.map(value => this.allRoles.get(value)));
    }

    this.userService.manageAnyUser(this.userForm.value, this.userForm.value)
    .subscribe(() => {
      this.alert.success('manageUser.success');
      this.router.navigate(['/users']);
    });
  }

  onClickCancel() {
    this.router.navigate(['/users']);
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.userForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
