package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Card;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface CardRepository extends CustomJpaRepository<Card, Long> {

}
