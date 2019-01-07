package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.exception.role.RoleNotFoundException;
import com.adach.scrumote.repository.RoleRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleInternalService extends AbstractInternalService<Role> {

  private static final String DEVELOPER_ROLE_NAME = "DEVELOPER";
  private static final Sort NAME_ASC_SORT = new Sort(Sort.Direction.ASC, "name");

  private final RoleRepository repository;

  //region Repository methods calls
  public Role findDeveloperRole() {
    return repository
        .findByName(DEVELOPER_ROLE_NAME)
        .orElseThrow(() -> new RoleNotFoundException("Role 'DEVELOPER' does not exist."));
  }

  public List<Role> findByIds(Set<Long> roleIds) {
    return repository.findAllById(roleIds);
  }

  public List<Role> findAll() {
    return repository.findAll(NAME_ASC_SORT);
  }
  //endregion
}
