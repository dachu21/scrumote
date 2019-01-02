import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, DeckService, PlanningService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Deck} from '../../_models';

@Component({
  selector: 'app-planning',
  templateUrl: 'edit-planning.component.html',
  styleUrls: ['./edit-planning.component.css']
})
export class EditPlanningComponent implements OnInit {

  readonly planningType!: string;
  planningForm: FormGroup;
  allDecks = new Map<string, Deck>();

  constructor(readonly auth: AuthenticationService, private router: Router,
              private planningService: PlanningService, private route: ActivatedRoute,
              private alert: AlertService, private formBuilder: FormBuilder,
              private deckService: DeckService) {

    const planningToEdit = this.planningService.planningToEdit;
    this.planningService.planningToEdit = undefined;

    if (this.route.snapshot.url[0].path === 'edit-planning') {
      this.planningType = 'edit';
      if (!planningToEdit) {
        this.alert.error('editPlanning.edit.noPlanningLoaded');
        this.router.navigate(['/all-plannings']);
      }
    } else if (this.route.snapshot.url[0].path === 'create-planning') {
      this.planningType = 'create';
    }

    this.planningForm = this.formBuilder.group({
      id: [''],
      version: [''],
      deckId: [''],

      code: [planningToEdit && planningToEdit.code || '', Validators.required],
      name: [planningToEdit && planningToEdit.name || '', Validators.required],
      deckName: [planningToEdit && planningToEdit.deckName || '', [Validators.required]],
      description: [planningToEdit && planningToEdit.description || ''],
    });

    if (planningToEdit) {
      this.planningForm.controls['id'].setValue(planningToEdit.id);
      this.planningForm.controls['version'].setValue(planningToEdit.version);
    }
  }

  ngOnInit() {
    this.deckService.getAllDecks().subscribe((response: Deck[]) => {
      this.allDecks = response.reduce(function (deckMap, deck) {
        deckMap.set(deck.name, deck);
        return deckMap;
      }, this.allDecks);
    });
  }

  onSubmit() {

    const selectedDeck = this.allDecks.get(this.planningForm.controls['deckName'].value);
    if (selectedDeck) {
      this.planningForm.controls['deckId']
      .setValue(selectedDeck.id);
    }

    if (this.planningType === 'edit') {
      this.planningService.updatePlanning(this.planningForm.value)
      .subscribe(() => {
        this.alert.success('editPlanning.edit.success');
        this.router.navigate(['/all-plannings']);
      });
    } else if (this.planningType === 'create') {
      this.planningService.createPlanning(this.planningForm.value)
      .subscribe(() => {
        this.alert.success('editPlanning.create.success');
        this.router.navigate(['/all-plannings']);
      });
    }
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.planningForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
