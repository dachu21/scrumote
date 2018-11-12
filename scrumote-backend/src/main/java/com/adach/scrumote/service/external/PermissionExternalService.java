package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.PermissionMapper;
import com.adach.scrumote.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionExternalService {

  private final PermissionRepository repository;
  private final PermissionMapper mapper;
}
