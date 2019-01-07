export class Card {

  level: number;
  value: string;

  constructor(level: number, value: string) {
    this.level = level;
    this.value = value;
  }

  static create(level: number, value: string) {
    return new Card(level, value);
  }
}
