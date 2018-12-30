export class User {

  id: number | null;
  version: number | null;

  username: string;
  email: string;
  firstName?: string;
  lastName?: string;

  constructor(id: number | null, version: number | null,
              username: string, email: string,
              firstName: string | undefined, lastName: string | undefined) {
    this.id = id;
    this.version = version;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  static create(username: string, email: string,
                firstName?: string, lastName?: string) {
    return new User(null, null, username, email, firstName, lastName);
  }
}
