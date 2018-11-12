package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.DeckMapper;
import com.adach.scrumote.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckExternalService {

  private final DeckRepository repository;
  private final DeckMapper mapper;
}
