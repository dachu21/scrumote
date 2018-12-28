import {User} from './user';
import {Password} from './password';

export class UserWithPassword {

  user: User;
  password: Password;

  constructor(user: User, password: Password) {
    this.user = user;
    this.password = password;
  }
}
