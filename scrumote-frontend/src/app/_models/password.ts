export class Password {

  newPassword: string;
  oldPassword: string | null | undefined;

  constructor(newPassword: string, oldPassword?: string | null) {
    this.newPassword = newPassword;
    this.oldPassword = oldPassword;
  }
}
