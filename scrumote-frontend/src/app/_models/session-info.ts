export class SessionInfo {
  id: number;
  username: string;
  authorities: string[];

  constructor(id: number, username: string, authorities: string[]) {
    this.id = id;
    this.username = username;
    this.authorities = authorities;
  }

  static createAnonymous() {
    return new SessionInfo(-1, 'ANONYMOUS', []);
  }
}
