package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.exception.deck.DeckNotFoundException;
import com.adach.scrumote.repository.DeckRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckInternalService {

  private final DeckRepository repository;

  public Deck findById(Long id) {
    Optional<Deck> deckOpt = repository.findById(id);
    if (deckOpt.isPresent()) {
      return deckOpt.get();
    } else {
      throw new DeckNotFoundException(String.format("Deck with id %d does not exist.", id));
    }
  }
}
