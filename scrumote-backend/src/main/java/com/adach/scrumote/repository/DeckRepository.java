package com.adach.scrumote.repository;

import com.adach.scrumote.entity.Deck;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface DeckRepository extends JpaRepository<Deck, Long> {

  //region Overriden
  @Override
  Optional<Deck> findById(Long id);
  //endregion
}
