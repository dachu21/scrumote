package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.VoteMapper;
import com.adach.scrumote.service.internal.VoteInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteExternalService {

  private final VoteInternalService internalService;
  private final VoteMapper mapper;
}
