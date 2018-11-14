package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.exception.planning.PlanningNotFoundException;
import com.adach.scrumote.repository.PlanningRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningInternalService {

  private final PlanningRepository repository;

  public Planning findById(Long id) {
    Optional<Planning> planningOpt = repository.findById(id);
    if (planningOpt.isPresent()) {
      return planningOpt.get();
    } else {
      throw new PlanningNotFoundException(String.format("Planning with id %d does not exist.", id));
    }
  }

  public List<Planning> findAll() {
    return repository.findAll();
  }
}
