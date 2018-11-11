package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.VoteMapper;
import com.adach.scrumote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VotePublicService {

  private final VoteRepository repository;
  private final VoteMapper mapper;
}
