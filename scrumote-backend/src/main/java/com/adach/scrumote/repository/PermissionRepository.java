package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface PermissionRepository extends CustomJpaRepository<Role, Long> {

}
