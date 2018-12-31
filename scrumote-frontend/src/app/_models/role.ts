export class Role {

  id: number | null;
  version: number | null;

  name: string;

  constructor(id: number | null, version: number | null, name: string) {
    this.id = id;
    this.version = version;
    this.name = name;
  }

  static create(name: string) {
    return new Role(null, null, name);
  }
}
