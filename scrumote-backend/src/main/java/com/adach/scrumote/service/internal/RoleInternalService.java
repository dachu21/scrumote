package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.exception.role.RoleNotFoundException;
import com.adach.scrumote.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleInternalService {

  private static final String DEVELOPER_ROLE_NAME = "DEVELOPER";

  private final RoleRepository repository;

  //region Repository methods calls
  public Role findDeveloperRole() {
    return repository
        .findByName(DEVELOPER_ROLE_NAME)
        .orElseThrow(() -> new RoleNotFoundException("Role 'DEVELOPER' does not exist."));
  }
  //endregion
}
