package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Vote;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface VoteRepository extends CustomJpaRepository<Vote, Long> {

}
