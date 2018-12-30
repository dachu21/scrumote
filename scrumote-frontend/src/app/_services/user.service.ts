import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User, UserWithPassword} from '../_models';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  registerUser(registerForm: RegisterForm) {
    const user: User = User.create(registerForm.username, registerForm.email,
        registerForm.firstName, registerForm.lastName);
    const password: Password = Password.create(registerForm.password);
    const userWithPassword: UserWithPassword = UserWithPassword.create(user, password);
    return this.http.post('api/users/register', userWithPassword);
  }
}
