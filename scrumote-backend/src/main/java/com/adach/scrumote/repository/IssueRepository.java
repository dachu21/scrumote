package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface IssueRepository extends CustomJpaRepository<Issue, Long> {

}
