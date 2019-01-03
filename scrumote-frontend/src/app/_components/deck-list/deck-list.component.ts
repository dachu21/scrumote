import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, DeckService} from '../../_services';
import {Deck} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';

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
              private deckService: DeckService) {
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

  deletePlanning(deck: Deck) {
    this.deckService.deleteDeck(deck).subscribe(() => {
      this.refreshDataSource();
      this.alert.success('deckList.delete.success');
    });
  }
}
