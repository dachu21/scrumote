package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.UserHistory;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface UserHistoryRepository extends CustomJpaRepository<UserHistory, Long> {

}
