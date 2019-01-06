package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface IssueRepository extends CustomJpaRepository<Issue, Long> {

  List<Issue> findAllByPlanning(Planning planning, Sort sort);
}
