package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface PlanningRepository extends CustomJpaRepository<Planning, Long> {

  List<Planning> findAllByUsersContains(User user);

  boolean existsByName(String name);
}
