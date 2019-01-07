import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {VoteDialogData} from '../../_interfaces';

@Component({
  selector: 'app-vote-dialog',
  templateUrl: 'vote-dialog.component.html',
  styleUrls: ['./vote-dialog.component.css'],
})
export class VoteDialogComponent {

  cardValue?: string;

  constructor(public dialogRef: MatDialogRef<VoteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) readonly dialogData: VoteDialogData) {
  }

  cancel(): void {
    this.dialogRef.close();
  }

}
