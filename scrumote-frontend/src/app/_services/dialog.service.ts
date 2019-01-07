import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AreYouSureDialogComponent, VoteDialogComponent} from '../_dialogs';
import {Deck} from '../_models';

@Injectable()
export class DialogService {

  private VOTE_DIALOG_WIDTH = '300px';
  private ARE_YOU_SURE_DIALOG_WIDTH = '300px';

  constructor(private dialog: MatDialog) {
  }

  openVoteDialog(i18nHeaderCode: string, deck: Deck) {
    return this.dialog.open(VoteDialogComponent, {
      width: this.VOTE_DIALOG_WIDTH,
      data: {
        deck: deck,
        i18nHeaderCode: i18nHeaderCode
      }
    });
  }

  openAreYouSureDialog() {
    return this.dialog.open(AreYouSureDialogComponent, {
      width: this.ARE_YOU_SURE_DIALOG_WIDTH
    });
  }
}
