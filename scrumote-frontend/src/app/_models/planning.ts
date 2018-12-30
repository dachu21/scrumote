export class Planning {

  id: number | null;
  version: number | null;

  code: string;
  name: string;
  deckId: string;
  users: number[];
  description?: string;

  finished: string | null;
  moderatorId: string | null;

  deckName: string | null;
  moderatorUsername: string | null;

  constructor(id: number | null, version: number | null,
              code: string, name: string, deckId: string, users: number[],
              description: string | undefined,
              finished: string | null, moderatorId: string | null,
              deckName: string | null, moderatorUsername: string | null) {
    this.id = id;
    this.version = version;
    this.code = code;
    this.name = name;
    this.deckId = deckId;
    this.users = users;
    this.description = description;
    this.finished = finished;
    this.moderatorId = moderatorId;
    this.deckName = deckName;
    this.moderatorUsername = moderatorUsername;
  }

  static create(code: string, name: string, deckId: string, users: number[],
                description?: string) {
    return new Planning(null, null, code, name, deckId, users, description,
        null, null, null, null);
  }
}
