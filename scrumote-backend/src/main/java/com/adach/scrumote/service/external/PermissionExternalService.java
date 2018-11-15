package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.mapper.PermissionMapper;
import com.adach.scrumote.service.internal.PermissionInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionExternalService {

  private final PermissionInternalService internalService;
  private final PermissionMapper mapper;
}
