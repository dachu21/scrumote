package com.adach.scrumote.service.external;

import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.exception.planning.PlanningAlreadyFinishedException;
import com.adach.scrumote.exception.planning.PlanningNotFoundException;
import com.adach.scrumote.mapper.PlanningMapper;
import com.adach.scrumote.repository.PlanningRepository;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningExternalService {

  private final PlanningRepository repository;
  private final PlanningMapper mapper;

  private final UserHistoryInternalService userHistoryInternalService;

  public void finish(Long id) {
    Planning planning = findIfExists(id);
    validateNotFinished(planning);
    planning.setFinished(true);
    repository.save(planning);
    userHistoryInternalService.updateUsersHistoryForPlanning(planning);
  }

  private Planning findIfExists(Long id) {
    Optional<Planning> planningOpt = repository.findById(id);
    if (planningOpt.isPresent()) {
      return planningOpt.get();
    } else {
      throw new PlanningNotFoundException(String.format("Planning with id %d does not exist.", id));
    }
  }

  private void validateNotFinished(Planning planning) {
    if (planning.isFinished()) {
      throw new PlanningAlreadyFinishedException(
          String.format("Planning with id %d is already finished.", planning.getId()));
    }
  }

  public List<PlanningSimpleDto> findAll() {
    return repository.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }
}
