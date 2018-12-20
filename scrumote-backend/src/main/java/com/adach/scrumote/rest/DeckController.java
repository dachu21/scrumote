package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.rest.PrefixedRestController;
import com.adach.scrumote.dto.complex.DeckWithCardsDto;
import com.adach.scrumote.service.external.DeckExternalService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckController extends AbstractController {

  private final DeckExternalService deckExternalService;

  @PreAuthorize("hasAnyAuthority('createDeck')")
  @PostMapping("/decks")
  public ResponseEntity<?> createDeck(@RequestBody DeckWithCardsDto dto) {
    Long id = deckExternalService.createDeck(dto);
    URI location = buildLocationUri(id);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getDeck')")
  @GetMapping("/decks/{id}")
  public DeckWithCardsDto getDeckWithCards(@PathVariable Long id) {
    return deckExternalService.getDeckWithCards(id);
  }

  @PreAuthorize("hasAnyAuthority('updateDeck')")
  @PutMapping("/decks/{id}")
  public ResponseEntity<?> updateDeck(@PathVariable Long id, @RequestBody DeckWithCardsDto dto) {
    deckExternalService.updateDeck(id, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('deleteDeck')")
  @DeleteMapping("/decks/{id}")
  public ResponseEntity<?> deleteDeck(@PathVariable Long id) {
    deckExternalService.deleteDeck(id);
    return ResponseEntity.noContent().build();
  }
}
