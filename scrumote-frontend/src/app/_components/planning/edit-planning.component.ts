import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, PlanningService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';

@Component({
  selector: 'app-planning',
  templateUrl: 'edit-planning.component.html',
  styleUrls: ['./edit-planning.component.css']
})
export class EditPlanningComponent implements OnInit {

  readonly planningType!: string;
  planningForm: FormGroup;

  constructor(readonly auth: AuthenticationService, private router: Router,
              private planningService: PlanningService, private route: ActivatedRoute,
              private alert: AlertService, private formBuilder: FormBuilder) {

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

      code: [planningToEdit && planningToEdit.code || '', Validators.required],
      name: [planningToEdit && planningToEdit.name || '', Validators.required],
      deckId: [planningToEdit && planningToEdit.deckId || '', [Validators.required]],
      description: [planningToEdit && planningToEdit.description || ''],
    });

    if (planningToEdit) {
      this.planningForm.controls['id'].setValue(planningToEdit.id);
      this.planningForm.controls['version'].setValue(planningToEdit.version);
    }
  }

  ngOnInit() {
  }

  onSubmit() {
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
