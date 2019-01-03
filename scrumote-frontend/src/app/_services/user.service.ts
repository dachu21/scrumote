import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User, UserWithPassword} from '../_models';
import {ifMatchOptions} from '../_functions';

@Injectable()
export class UserService {

  private planningBaseUrl = '/plannings/';
  private baseUrl = '/users';

  userToEdit?: User;

  constructor(private http: HttpClient) {
  }

  private convertToUserWithPassword(registerForm: NewUserForm) {
    const user: User = User.create(registerForm.username, registerForm.email,
        registerForm.firstName, registerForm.lastName);
    const password: Password = Password.create(registerForm.password);
    return UserWithPassword.create(user, password);
  }

  registerUser(registerForm: NewUserForm) {
    return this.http.post<UserWithPassword>(this.baseUrl + '/register',
        this.convertToUserWithPassword(registerForm));
  }

  createUser(registerForm: NewUserForm) {
    return this.http.post<UserWithPassword>(this.baseUrl + '/create',
        this.convertToUserWithPassword(registerForm));
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

}
