package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.RoleMapper;
import com.adach.scrumote.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleExternalService {

  private final RoleRepository repository;
  private final RoleMapper mapper;
}
