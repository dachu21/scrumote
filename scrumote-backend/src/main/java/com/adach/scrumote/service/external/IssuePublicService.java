package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.IssueMapper;
import com.adach.scrumote.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssuePublicService {

  private final IssueRepository repository;
  private final IssueMapper mapper;
}
