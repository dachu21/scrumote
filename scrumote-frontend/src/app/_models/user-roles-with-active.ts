export class UserRolesWithActive {

  active: boolean;
  roles: number[];

  constructor(active: boolean, roles: number[]) {
    this.active = active;
    this.roles = roles;
  }

  static create(active: boolean, roles: number[]) {
    return new UserRolesWithActive(active, roles);
  }
}
