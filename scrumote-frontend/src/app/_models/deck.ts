import {Card} from './card';

export class Deck {

  id: number | null;
  version: number | null;

  name: string;
  cards: Card[];


  constructor(id: number | null, version: number | null, name: string, cards: Card[]) {
    this.id = id;
    this.version = version;
    this.name = name;
    this.cards = cards;
  }

  static create(name: string, cards: Card[]) {
    return new Deck(null, null, name, cards);
  }
}
