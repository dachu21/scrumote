import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, DeckService} from '../../_services';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material';
import {Card} from '../../_models';

@Component({
  selector: 'app-edit-deck',
  templateUrl: 'edit-deck.component.html'
})
export class EditDeckComponent {

  readonly ENTER_KEY = ENTER;
  readonly deckType!: string;
  deckForm: FormGroup;
  cards: Card[] = [];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private deckService: DeckService) {

    const deckToEdit = this.deckService.deckToEdit;
    this.deckService.deckToEdit = undefined;

    if (this.route.snapshot.url[0].path === 'edit-deck') {
      this.deckType = 'edit';
      if (!deckToEdit) {
        this.alert.error('editDeck.edit.noDeckLoaded');
        this.router.navigate(['/decks']);
      }
    } else if (this.route.snapshot.url[0].path === 'create-deck') {
      this.deckType = 'create';
    }

    this.deckForm = this.formBuilder.group({
      id: [''],
      version: [''],

      name: [deckToEdit && deckToEdit.name || '', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(32)]]
    });

    if (deckToEdit) {
      this.deckForm.controls['id'].setValue(deckToEdit.id);
      this.deckForm.controls['version'].setValue(deckToEdit.version);
      this.cards = deckToEdit.cards;
    }
  }

  onSubmit() {
    this.assignCardLevels();

    if (this.deckType === 'edit') {
      this.deckService.updateDeck(this.deckForm.value)
      .subscribe(() => {
        this.alert.success('editDeck.edit.success');
        this.router.navigate(['/decks']);
      });
    } else if (this.deckType === 'create') {
      this.deckService.createDeck(this.deckForm.value)
      .subscribe(() => {
        this.alert.success('editDeck.create.success');
        this.router.navigate(['/decks']);
      });
    }
  }

  private assignCardLevels() {
    let i = 1;
    for (const card of this.cards) {
      card.level = i++;
    }
    this.deckForm.addControl('cards', new FormControl(this.cards));
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.deckForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }

  addCard(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim()) {
      this.cards.push(Card.create(0, value.trim()));
    }
    if (input) {
      input.value = '';
    }
  }

  removeCard(card: Card): void {
    const index = this.cards.indexOf(card);
    if (index >= 0) {
      this.cards.splice(index, 1);
    }
  }
}
