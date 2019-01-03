package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface UserRepository extends CustomJpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  List<User> findAllByRolesContains(Role role);
}
