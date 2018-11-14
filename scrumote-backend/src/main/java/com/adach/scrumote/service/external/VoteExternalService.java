package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.VoteMapper;
import com.adach.scrumote.repository.VoteRepository;
import com.adach.scrumote.service.internal.VoteInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteExternalService {

  private final VoteInternalService internalService;
  private final VoteRepository repository;
  private final VoteMapper mapper;
}
