package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.exception.issue.IssueAlreadyActiveException;
import com.adach.scrumote.exception.issue.IssueAlreadyEstimatedException;
import com.adach.scrumote.exception.issue.IssueIterationIsCurrentlyActiveException;
import com.adach.scrumote.exception.issue.IssueIterationIsNotCurrentlyActiveException;
import com.adach.scrumote.exception.issue.IssueMismatchException;
import com.adach.scrumote.exception.issue.IssueNotFoundException;
import com.adach.scrumote.repository.IssueRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueInternalService extends AbstractInternalService<Issue> {

  private final IssueRepository repository;

  //region Repository methods calls
  public Issue save(Issue issue) {
    return repository.save(issue);
  }

  public Issue findById(Long id) {
    Optional<Issue> issueOpt = repository.findById(id);
    return issueOpt.orElseThrow(
        () -> new IssueNotFoundException(String.format("Issue with id %d does not exist.", id)));
  }

  public List<Issue> findAllByPlanning(Planning planning) {
    return repository.findAllByPlanning(planning);
  }

  public void delete(Issue issue) {
    repository.delete(issue);
  }
  //endregion

  //region Validation methods
  public void validateBelongsToPlanningWithId(Issue issue, Long planningId) {
    if (!issue.getPlanning().getId().equals(planningId)) {
      throw new IssueMismatchException(String
          .format("Issue with id %d does not belong to planning with id %d.", issue.getId(),
              planningId));
    }
  }

  public void validateNotEstimated(Issue issue) {
    if (issue.getEstimate().isPresent()) {
      throw new IssueAlreadyEstimatedException(
          String.format("Issue with id %d is already estimated", issue.getId()));
    }
  }

  public void validateNotActive(Issue issue) {
    if (issue.isActive()) {
      throw new IssueAlreadyActiveException(
          String.format("Issue with id %d is active.", issue.getId()));
    }
  }

  public void validateIsNotCurrentlyActiveIteration(Issue issue, Integer iteration) {
    if (issue.isActive() && iteration.equals(issue.getFinishedIterations() + 1)) {
      throw new IssueIterationIsCurrentlyActiveException(
          String.format("Iteration no. %d is currently active iteration of issue with id %d.",
              iteration, issue.getId()));
    }
  }

  public void validateIsCurrentlyActiveIteration(Issue issue, Integer iteration) {
    if (!issue.isActive() || !iteration.equals(issue.getFinishedIterations() + 1)) {
      throw new IssueIterationIsNotCurrentlyActiveException(
          String.format("Iteration no. %d is not currently active iteration of issue with id %d.",
              iteration, issue.getId()));
    }
  }
  //endregion
}
