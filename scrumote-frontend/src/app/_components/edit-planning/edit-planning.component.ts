import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, DeckService, PlanningService, UserService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Deck, User} from '../../_models';

@Component({
  selector: 'app-edit-planning',
  templateUrl: 'edit-planning.component.html'
})
export class EditPlanningComponent implements OnInit {

  readonly planningType!: string;
  planningForm: FormGroup;
  allDecks = new Map<string, number>();
  allDecksKeys?: string[];
  allDevelopers = new Map<string, number>();
  allDevelopersKeys?: string[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private planningService: PlanningService,
              private deckService: DeckService,
              private userService: UserService) {

    const planningToEdit = this.planningService.planningToEdit;
    this.planningService.planningToEdit = undefined;

    if (this.route.snapshot.url[0].path === 'edit-planning') {
      this.planningType = 'edit';
      if (!planningToEdit) {
        this.alert.error('editDeck.edit.noPlanningLoaded');
        this.router.navigate(['/all-plannings']);
      }
    } else if (this.route.snapshot.url[0].path === 'create-planning') {
      this.planningType = 'create';
    }

    this.planningForm = this.formBuilder.group({
      id: [''],
      version: [''],
      deckId: [''],
      users: [''],

      code: [planningToEdit && planningToEdit.code || '', Validators.required],
      name: [planningToEdit && planningToEdit.name || '', Validators.required],
      deckName: [planningToEdit && planningToEdit.deckName || '', [Validators.required]],
      usersUsernames: [planningToEdit && planningToEdit.usersUsernames || ''],
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
        if (deck.id) {
          deckMap.set(deck.name, deck.id);
        }
        return deckMap;
      }, this.allDecks);
      this.allDecksKeys = Array.from(this.allDecks.keys());
    });
    this.userService.getAllDevelopers().subscribe((response: User[]) => {
      this.allDevelopers = response.reduce(function (userMap, user) {
        if (user.id) {
          userMap.set(user.username, user.id);
        }
        return userMap;
      }, this.allDevelopers);
      this.allDevelopersKeys = Array.from(this.allDevelopers.keys());
    });
  }

  onSubmit() {

    const selectedDeckId = this.allDecks.get(this.planningForm.controls['deckName'].value);
    if (selectedDeckId) {
      this.planningForm.controls['deckId']
      .setValue(selectedDeckId);
    }
    const selectedUsernames = <string[]>this.planningForm.controls['usersUsernames'].value;
    if (selectedUsernames) {
      this.planningForm.controls['users']
      .setValue(selectedUsernames.map(value => this.allDevelopers.get(value)));
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
