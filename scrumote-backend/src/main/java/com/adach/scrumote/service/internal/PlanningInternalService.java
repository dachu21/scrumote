package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.exception.planning.PlanningAlreadyFinishedException;
import com.adach.scrumote.exception.planning.PlanningForbiddenException;
import com.adach.scrumote.exception.planning.PlanningNotFinishedException;
import com.adach.scrumote.exception.planning.PlanningNotFoundException;
import com.adach.scrumote.repository.PlanningRepository;
import com.adach.scrumote.service.security.CurrentUser;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningInternalService {

  private final PlanningRepository repository;

  //region Repository methods calls
  public Planning save(Planning planning) {
    return repository.save(planning);
  }

  public Planning findById(Long id) {
    Optional<Planning> planningOpt = repository.findById(id);
    return planningOpt.orElseThrow(() -> new PlanningNotFoundException(
        String.format("Planning with id %d does not exist.", id)));
  }

  public List<Planning> findAll() {
    return repository.findAll();
  }

  public List<Planning> findAllByUser(User user) {
    return repository.findAllByUsersContains(user);
  }

  public void delete(Planning planning) {
    repository.delete(planning);
  }
  //endregion

  //region Validation methods
  public void validateContainsCurrentUserIfNotAuthorized(Planning planning) {
    if (!CurrentUser.hasAuthority("getAnyPlanning") &&
        !planning.containsUser(CurrentUser.get())) {
      throw new PlanningForbiddenException(String
          .format("Current user does not have access to planning with id %d.", planning.getId()));
    }
  }

  public void validateHasModerator(Planning planning, User user) {
    if (!planning.hasModerator(user)) {
      throw new PlanningForbiddenException(
          String.format("Current user is not moderator of planning with id %d.", planning.getId()));
    }
  }

  public void validateNotFinished(Planning planning) {
    if (planning.isFinished()) {
      throw new PlanningAlreadyFinishedException(
          String.format("Planning with id %d is already finished.", planning.getId()));
    }
  }

  public void validateFinished(Planning planning) {
    if (!planning.isFinished()) {
      throw new PlanningNotFinishedException(
          String.format("Planning with id %d is not finished yet.", planning.getId()));
    }
  }
  //endregion
}
