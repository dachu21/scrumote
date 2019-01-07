import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User, UserRolesWithActive, UserWithPassword} from '../_models';
import {ifMatchOptions} from '../_functions';
import {NewUserForm} from '../_interfaces';

@Injectable()
export class UserService {

  private planningBaseUrl = '/plannings/';
  private baseUrl = '/users';

  userToEdit?: User;

  constructor(private http: HttpClient) {
  }

  private convertToUserWithPassword(newUserForm: NewUserForm) {
    const user: User = User.create(newUserForm.username, newUserForm.email,
        newUserForm.firstName, newUserForm.lastName);
    const password: Password = Password.create(newUserForm.password);
    return UserWithPassword.create(user, password);
  }

  registerUser(newUserForm: NewUserForm) {
    return this.http.post<UserWithPassword>(this.baseUrl + '/register',
        this.convertToUserWithPassword(newUserForm));
  }

  createUser(newUserForm: NewUserForm) {
    return this.http.post<UserWithPassword>(this.baseUrl + '/create',
        this.convertToUserWithPassword(newUserForm));
  }

  getMyUser() {
    return this.http.get<User>(this.baseUrl + '/my');
  }

  getAnyUser(id: number) {
    return this.http.get<User>(this.baseUrl + '/' + id);
  }

  getAllUsers() {
    return this.http.get<User[]>(this.baseUrl);
  }

  getAllDevelopers() {
    return this.http.get<User[]>(this.baseUrl + '/developers');
  }

  getUsersForPlanning(planningId: number) {
    return this.http.get<User[]>(this.planningBaseUrl + planningId + this.baseUrl);
  }

  updateMyUser(user: User) {
    return this.http.put<User>(this.baseUrl + '/my',
        user, ifMatchOptions(user.version));
  }

  updateAnyUser(user: User) {
    return this.http.put<User>(this.baseUrl + '/' + user.id,
        user, ifMatchOptions(user.version));
  }

  updateMyUserPassword(user: User, password: Password) {
    return this.http.put<Password>(this.baseUrl + '/my/password',
        password, ifMatchOptions(user.version));
  }

  updateAnyUsersPassword(user: User, password: Password) {
    return this.http.put<Password>(this.baseUrl + '/' + user.id + '/password',
        password, ifMatchOptions(user.version));
  }

  manageAnyUser(user: User, userRolesWithActive: UserRolesWithActive) {
    return this.http.put<UserRolesWithActive>(this.baseUrl + '/' + user.id + '/manage',
        userRolesWithActive, ifMatchOptions(user.version));
  }

}
