package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.mapper.RoleMapper;
import com.adach.scrumote.service.internal.RoleInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleExternalService {

  private final RoleInternalService internalService;
  private final RoleMapper mapper;
}
