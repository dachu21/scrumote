package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Planning;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface PlanningRepository extends CustomJpaRepository<Planning, Long> {

}
