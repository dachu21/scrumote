package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.IssueMapper;
import com.adach.scrumote.repository.IssueRepository;
import com.adach.scrumote.service.internal.IssueInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueExternalService {

  private final IssueInternalService internalService;
  private final IssueRepository repository;
  private final IssueMapper mapper;
}
