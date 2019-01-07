package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.RoleSimpleDto;
import com.adach.scrumote.mapper.RoleMapper;
import com.adach.scrumote.service.internal.RoleInternalService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleExternalService {

  private final RoleInternalService internalService;
  private final RoleMapper mapper;

  @PreAuthorize("hasAnyAuthority('getAllRoles')")
  public List<RoleSimpleDto> getAllRoles() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }
}
