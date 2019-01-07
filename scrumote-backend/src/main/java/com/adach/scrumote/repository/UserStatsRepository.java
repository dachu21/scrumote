package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.UserStats;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface UserStatsRepository extends CustomJpaRepository<UserStats, Long> {

}
