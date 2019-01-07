import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-are-you-sure-dialog',
  templateUrl: 'are-you-sure-dialog.component.html',
  styleUrls: ['./are-you-sure-dialog.component.css'],
})
export class AreYouSureDialogComponent {

  constructor(public dialogRef: MatDialogRef<AreYouSureDialogComponent>) {
  }

  cancel(): void {
    this.dialogRef.close();
  }

}
