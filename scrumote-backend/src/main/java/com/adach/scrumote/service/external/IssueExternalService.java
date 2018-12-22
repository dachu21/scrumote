package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.mapper.IssueMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.IssueInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.VoteInternalService;
import com.adach.scrumote.service.security.CurrentUser;
import com.adach.scrumote.sse.SseService;
import com.adach.scrumote.sse.events.AllUsersVotedEvent;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueExternalService {

  private final IssueInternalService internalService;
  private final IssueMapper mapper;

  private final PlanningInternalService planningInternalService;
  private final DeckInternalService deckInternalService;
  private final VoteInternalService voteInternalService;

  private final SseService sseService;

  @PreAuthorize("hasAnyAuthority('createIssue')")
  public Long createIssue(Long planningId, IssueSimpleDto dto) {
    Planning planning = planningInternalService.findById(planningId);
    planningInternalService.validateNotFinished(planning);
    planningInternalService.validateHasModerator(planning, CurrentUser.get());

    Issue issue = mapper.mapToEntity(dto);
    issue.setFinishedIterations(0);
    issue.setEstimate(null);
    issue.setActive(false);
    issue.setPlanning(planning);
    setDescriptionToNullIfBlank(issue);

    return internalService.save(issue).getId();
  }

  @PreAuthorize("hasAnyAuthority('getIssue')")
  public IssueSimpleDto getIssue(Long planningId, Long issueId) {
    Issue issue = internalService.findById(issueId);
    Planning planning = findPlanningAndValidateItsId(issue, planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return mapper.mapToSimpleDto(issue);
  }

  @PreAuthorize("hasAnyAuthority('getIssuesForPlanning')")
  public List<IssueSimpleDto> getIssuesForPlanning(Long planningId) {
    Planning planning = planningInternalService.findById(planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return internalService.findAllByPlanning(planning).stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updateIssue')")
  public void updateIssue(Long planningId, Long issueId, Long version, IssueSimpleDto dto) {
    Issue issue = findIssueAndValidateVersion(issueId, version);
    Planning planning = findPlanningAndValidateItsId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    issue.setCode(dto.getCode());
    issue.setName(dto.getName());
    issue.setDescription(dto.getDescription());
    setDescriptionToNullIfBlank(issue);
  }

  @PreAuthorize("hasAnyAuthority('activateIssue')")
  public void activateIssue(Long planningId, Long issueId, Long version) {
    Issue issue = findIssueAndValidateVersion(issueId, version);
    Planning planning = findPlanningAndValidateItsId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    issue.setActive(true);
  }

  @PreAuthorize("hasAnyAuthority('estimateIssue')")
  public void estimateIssue(Long planningId, Long issueId, Long version, String cardValue) {
    Issue issue = findIssueAndValidateVersion(issueId, version);
    Planning planning = findPlanningAndValidateItsId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    Deck deck = planning.getDeck();
    deckInternalService.validateContainsCardWithValue(deck, cardValue);
    issue.setEstimate(cardValue);
  }

  @PreAuthorize("hasAnyAuthority('deleteIssue')")
  public void deleteIssue(Long planningId, Long issueId, Long version) {
    Issue issue = findIssueAndValidateVersion(issueId, version);
    Planning planning = findPlanningAndValidateItsId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    internalService.delete(issue);
  }

  @PreAuthorize("hasAnyAuthority('createVote')")
  public void deactivateIssueIfAllUsersVoted(Long id, Long voteId) {
    Issue issue = internalService.findById(id);
    Vote newVote = voteInternalService.findById(voteId);
    Integer newVoteIteration = newVote.getIteration();
    if (issue.isActive() && issue.getFinishedIterations().equals(newVoteIteration - 1)) {
      Long usersCount = (long) issue.getPlanning().getUsers().size();
      Long votesCount = issue.getVotes().stream()
          .filter(vote -> vote.getIteration().equals(newVoteIteration))
          .count();
      if (usersCount.equals(votesCount)) {
        issue.setActive(false);
        issue.setFinishedIterations(newVoteIteration);
        sendAllUsersVotedEvent(issue);
      }
    }
  }

  private Issue findIssueAndValidateVersion(Long id, Long version) {
    Issue issue = internalService.findById(id);
    internalService.validateVersion(issue, version);
    return issue;
  }

  private Planning findPlanningAndValidateItsId(Issue issue, Long planningId) {
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    return planning;
  }

  private void validateIssueAndPlanningForUpdateOrDelete(Issue issue, Planning planning) {
    planningInternalService.validateNotFinished(planning);
    planningInternalService.validateHasModerator(planning, CurrentUser.get());
    internalService.validateNotEstimated(issue);
    internalService.validateNotActive(issue);
  }

  private void setDescriptionToNullIfBlank(Issue issue) {
    if (issue.getDescription().isPresent() && issue.getDescription().get().isBlank()) {
      issue.setDescription(null);
    }
  }

  private void sendAllUsersVotedEvent(Issue issue) {
    AllUsersVotedEvent event = new AllUsersVotedEvent(
        issue.getPlanning().getId(), issue.getId(), issue.getFinishedIterations());
    sseService.sendSseEvent(event);
  }
}
