package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Deck;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface DeckRepository extends CustomJpaRepository<Deck, Long> {

  boolean existsByName(String name);
}
