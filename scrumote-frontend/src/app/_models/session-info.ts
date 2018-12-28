export class SessionInfo {
  id: number;
  name: string;
  authorities: Array<string>;

  constructor(id: number, name: string, authorities: Array<string>) {
    this.id = id;
    this.name = name;
    this.authorities = authorities;
  }
}
