import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, DeckService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';

@Component({
  selector: 'app-edit-deck',
  templateUrl: 'edit-deck.component.html'
})
export class EditDeckComponent implements OnInit {

  readonly deckType!: string;
  deckForm: FormGroup;

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

      name: [deckToEdit && deckToEdit.name || '', Validators.required],
    });

    if (deckToEdit) {
      this.deckForm.controls['id'].setValue(deckToEdit.id);
      this.deckForm.controls['version'].setValue(deckToEdit.version);
    }
  }

  ngOnInit() {
  }

  onSubmit() {
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

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.deckForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
