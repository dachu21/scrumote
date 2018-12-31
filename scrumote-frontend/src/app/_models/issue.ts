export class Issue {

  id: number | null;
  version: number | null;

  code: string;
  name: string;
  description?: string;

  finishedIterations: number | null;
  estimate: number | null;
  active: boolean | null;


  constructor(id: number | null, version: number | null,
              code: string, name: string, description: string | undefined,
              finishedIterations: number | null, estimate: number | null, active: boolean | null) {
    this.id = id;
    this.version = version;
    this.code = code;
    this.name = name;
    this.description = description;
    this.finishedIterations = finishedIterations;
    this.estimate = estimate;
    this.active = active;
  }

  static create(code: string, name: string, description?: string) {
    return new Issue(null, null, code, name, description, null, null, null);
  }
}
