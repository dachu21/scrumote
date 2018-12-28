import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User, UserWithPassword} from '../_models';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  register(registerForm: RegisterForm) {
    const user: User = new User(null, null, registerForm.username, registerForm.email,
        registerForm.firstName, registerForm.lastName);
    const password: Password = new Password(registerForm.password);
    const userWithPassword: UserWithPassword = new UserWithPassword(user, password);
    return this.http.post('api/users/register', userWithPassword);
  }
}
