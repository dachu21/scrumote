package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.Vote;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface VoteRepository extends CustomJpaRepository<Vote, Long> {

  List<Vote> findAllByIssueAndIteration(Issue issue, Integer iteration);

  List<Vote> findAllByIssueAndIterationNot(Issue issue, Integer iteration);

  boolean existsByIssueAndIterationAndUser(Issue issue, Integer iteration, User currentUser);
}
