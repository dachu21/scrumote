export class SystemFeature {

  id: number | null;
  version: number | null;

  code: string;
  enabled: boolean;

  constructor(id: number | null, version: number | null, code: string, enabled: boolean) {
    this.id = id;
    this.version = version;
    this.code = code;
    this.enabled = enabled;
  }

  static overwrite(to: SystemFeature, from: SystemFeature) {
    to.id = from.id;
    to.version = from.version;
    to.code = from.code;
    to.enabled = from.enabled;
  }
}
