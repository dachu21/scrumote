export class Password {

  newPassword: string;
  oldPassword?: string;

  constructor(newPassword: string, oldPassword?: string) {
    this.newPassword = newPassword;
    this.oldPassword = oldPassword;
  }

  static create(newPassword: string, oldPassword?: string) {
    return new Password(newPassword, oldPassword);
  }
}
