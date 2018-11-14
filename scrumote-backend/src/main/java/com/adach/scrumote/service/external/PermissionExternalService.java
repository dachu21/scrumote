package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.PermissionMapper;
import com.adach.scrumote.repository.PermissionRepository;
import com.adach.scrumote.service.internal.PermissionInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionExternalService {

  private final PermissionInternalService internalService;
  private final PermissionRepository repository;
  private final PermissionMapper mapper;
}
