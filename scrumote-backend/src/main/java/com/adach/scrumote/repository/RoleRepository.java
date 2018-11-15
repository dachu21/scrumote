package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface RoleRepository extends CustomJpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
