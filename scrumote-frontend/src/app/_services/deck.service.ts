import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Deck} from '../_models';
import {ifMatchOptions} from '../_functions';

@Injectable()
export class DeckService {

  private baseUrl = '/decks';

  constructor(private http: HttpClient) {
  }

  createDeck(deck: Deck) {
    return this.http.post<Deck>(this.baseUrl, deck);
  }

  getDeck(id: number) {
    return this.http.get<Deck>(this.baseUrl + '/' + id);
  }

  getAllDecks() {
    return this.http.get<Deck[]>(this.baseUrl);
  }

  updateDeck(deck: Deck) {
    return this.http.put<Deck>(this.baseUrl + '/' + deck.id,
        deck, ifMatchOptions(deck.version));
  }

  deleteDeck(deck: Deck) {
    return this.http.delete(this.baseUrl + '/' + deck.id,
        ifMatchOptions(deck.version));
  }
}
