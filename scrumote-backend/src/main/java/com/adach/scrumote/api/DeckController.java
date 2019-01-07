package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.complex.DeckWithCardsDto;
import com.adach.scrumote.service.external.DeckExternalService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestHeader;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckController extends AbstractController {

  private final DeckExternalService deckExternalService;

  @PreAuthorize("hasAnyAuthority('createDeck')")
  @PostMapping("/decks")
  public ResponseEntity<?> createDeck(@RequestBody @Valid DeckWithCardsDto dto) {
    Long newDeckId = deckExternalService.createDeck(dto);
    URI location = buildLocationUri(newDeckId);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getDeck')")
  @GetMapping("/decks/{deckId}")
  public DeckWithCardsDto getDeckWithCards(@PathVariable Long deckId) {
    return deckExternalService.getDeckWithCards(deckId);
  }

  @PreAuthorize("hasAnyAuthority('getAllDecks')")
  @GetMapping("/decks")
  public List<DeckWithCardsDto> getAllDecksWithCards() {
    return deckExternalService.getAllDecksWithCards();
  }

  @PreAuthorize("hasAnyAuthority('updateDeck')")
  @PutMapping("/decks/{deckId}")
  public ResponseEntity<?> updateDeck(@PathVariable Long deckId,
      @RequestBody @Valid DeckWithCardsDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    deckExternalService.updateDeck(deckId, version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('deleteDeck')")
  @DeleteMapping("/decks/{deckId}")
  public ResponseEntity<?> deleteDeck(@PathVariable Long deckId,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    deckExternalService.deleteDeck(deckId, version);
    return ResponseEntity.noContent().build();
  }
}
