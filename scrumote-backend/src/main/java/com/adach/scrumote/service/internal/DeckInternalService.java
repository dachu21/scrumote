package com.adach.scrumote.service.internal;

import com.adach.scrumote.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckInternalService {

  private final DeckRepository repository;
}
