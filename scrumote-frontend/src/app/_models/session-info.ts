export class SessionInfo {
  id: number;
  username: string;
  authorities: Array<string>;

  constructor(id: number, username: string, authorities: Array<string>) {
    this.id = id;
    this.username = username;
    this.authorities = authorities;
  }

  static createAnonymous() {
    return new SessionInfo(-1, 'ANONYMOUS', []);
  }
}
