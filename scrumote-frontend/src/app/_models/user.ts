export class User {

  id: number | null;
  version: number | null;
  username: string;
  email: string;
  firstName: string | null | undefined;
  lastName: string | null | undefined;

  constructor(id: number | null, version: number | null, username: string, email: string,
              firstName?: string | null, lastName?: string | null) {
    this.id = id;
    this.version = version;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
