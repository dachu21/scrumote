import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {AlertService, AuthenticationService, DeckService, DialogService} from '../../_services';
import {Deck} from '../../_models';


@Component({
  selector: 'app-deck-list',
  templateUrl: 'deck-list.component.html',
  styleUrls: ['./deck-list.component.css']
})
export class DeckListComponent implements OnInit {

  dataSource = new MatTableDataSource<Deck>();

  displayedColumns: string[]
      = ['name', 'cards', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private deckService: DeckService,
              private dialogService: DialogService) {
  }

  private refreshDataSource() {
    this.deckService.getAllDecks().subscribe((response: Deck[]) => {
      this.dataSource.data = response;
    });
  }

  ngOnInit() {
    this.refreshDataSource();
    this.dataSource.paginator = this.paginator;
  }

  editDeck(deck: Deck) {
    this.deckService.deckToEdit = deck;
    this.router.navigate(['/edit-deck']);
  }

  deleteDeck(deck: Deck) {
    this.dialogService.openAreYouSureDialog().afterClosed().subscribe(value => {
      if (value) {
        this.deckService.deleteDeck(deck).subscribe(() => {
          this.refreshDataSource();
          this.alert.success('deckList.delete.success');
        });
      }
    });
  }

  refreshPage() {
    this.router.navigate(['/decks']);
  }
}
