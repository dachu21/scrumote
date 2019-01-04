import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, IssueService, PlanningService} from '../../_services';
import {FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Planning} from '../../_models';

@Component({
  selector: 'app-edit-issue',
  templateUrl: 'edit-issue.component.html'
})
export class EditIssueComponent implements OnInit {

  readonly issueType!: string;
  issueForm: FormGroup;

  openedPlanning!: Planning;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private formBuilder: FormBuilder,
              private issueService: IssueService,
              private planningService: PlanningService) {

    const issueToEdit = this.issueService.issueToEdit;
    const openedPlanning = this.issueService.openedPlanning;
    this.issueService.issueToEdit = undefined;
    this.issueService.openedPlanning = undefined;

    if (!openedPlanning) {
      this.alert.error('editIssue.edit.noPlanningLoaded');
      this.router.navigate(['/home']);
    } else {
      this.openedPlanning = openedPlanning;
    }
    if (this.route.snapshot.url[0].path === 'edit-issue') {
      this.issueType = 'edit';
      if (!issueToEdit) {
        this.alert.error('editIssue.edit.noIssueLoaded');
        this.router.navigate(['/home']);
      }
    } else if (this.route.snapshot.url[0].path === 'create-issue') {
      this.issueType = 'create';
    }

    this.issueForm = this.formBuilder.group({
      id: [''],
      version: [''],

      code: [issueToEdit && issueToEdit.code || '', Validators.required],
      name: [issueToEdit && issueToEdit.name || '', Validators.required],
      description: [issueToEdit && issueToEdit.description || '']
    });

    if (issueToEdit) {
      this.issueForm.controls['id'].setValue(issueToEdit.id);
      this.issueForm.controls['version'].setValue(issueToEdit.version);
    }
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.openedPlanning.id) {
      if (this.issueType === 'edit') {
        this.issueService.updateIssue(this.openedPlanning.id, this.issueForm.value)
        .subscribe(() => {
          this.alert.success('editIssue.edit.success');
          this.navigateToOpenedPlanning();
        });
      } else if (this.issueType === 'create' && this.openedPlanning.id) {
        this.issueService.createIssue(this.openedPlanning.id, this.issueForm.value)
        .subscribe(() => {
          this.alert.success('editIssue.create.success');
          this.navigateToOpenedPlanning();
        });
      }
    }
  }

  navigateToOpenedPlanning() {
    this.planningService.planningToOpen = this.openedPlanning;
    this.router.navigate(['/planning']);
  }

  getErrorKeys(controlName: string) {
    const errors: ValidationErrors | null = this.issueForm.controls[controlName].errors;
    return errors && Object.keys(errors);
  }
}
