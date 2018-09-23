package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.Role;
import com.adach.scrumote.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleInternalService {

  private static final String STANDARD_USER_ROLE_NAME = "STANDARD";

  private final RoleRepository roleRepository;

  public Role findStandardUserRole() {
    return roleRepository
        .findByName(STANDARD_USER_ROLE_NAME)
        .orElseThrow(() -> new RuntimeException("No standard role in DB.")); //TODO inny wyjatek
  }
}
