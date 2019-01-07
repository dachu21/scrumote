export class User {

  id: number | null;
  version: number | null;

  username: string;
  email: string;
  firstName?: string;
  lastName?: string;

  active: boolean | null;

  roleNames: string[] | null;

  constructor(id: number | null, version: number | null,
              username: string, email: string,
              firstName: string | undefined, lastName: string | undefined,
              active: boolean | null,
              roleNames: string[] | null) {
    this.id = id;
    this.version = version;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.active = active;
    this.roleNames = roleNames;
  }

  static create(username: string, email: string,
                firstName?: string, lastName?: string) {
    return new User(null, null, username, email, firstName, lastName, null, null);
  }
}
