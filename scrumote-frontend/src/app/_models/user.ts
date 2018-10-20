export class User {

  id: number;
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;

  constructor(id: number, username: string, password: string, email: string, firstName: string, lastName: string) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}