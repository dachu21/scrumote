package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Vote;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface VoteRepository extends CustomJpaRepository<Vote, Long> {

  List<Vote> findAllByIssueAndIteration(Issue issue, Integer iteration, Sort sort);

  List<Vote> findAllByIssueAndIterationNot(Issue issue, Integer iteration, Sort sort);
}
