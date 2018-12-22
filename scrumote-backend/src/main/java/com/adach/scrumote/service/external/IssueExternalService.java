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
    issue.setPlanning(planning);

    return internalService.save(issue).getId();
  }

  @PreAuthorize("hasAnyAuthority('getIssue')")
  public IssueSimpleDto getIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
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
  public void updateIssue(Long planningId, Long id, IssueSimpleDto dto) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    issue.setCode(dto.getCode());
    issue.setName(dto.getName());
    issue.setDescription(dto.getDescription());
  }

  @PreAuthorize("hasAnyAuthority('activateIssue')")
  public void activateIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    issue.setActive(true);
  }

  @PreAuthorize("hasAnyAuthority('estimateIssue')")
  public void estimateIssue(Long planningId, Long id, String cardValue) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    Deck deck = planning.getDeck();
    deckInternalService.validateContainsCardWithValue(deck, cardValue);
    issue.setEstimate(cardValue);
  }

  @PreAuthorize("hasAnyAuthority('deleteIssue')")
  public void deleteIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
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

  private void validateIssueAndPlanningForUpdateOrDelete(Issue issue, Planning planning) {
    planningInternalService.validateNotFinished(planning);
    planningInternalService.validateHasModerator(planning, CurrentUser.get());
    internalService.validateNotEstimated(issue);
    internalService.validateNotActive(issue);
  }

  private void sendAllUsersVotedEvent(Issue issue) {
    AllUsersVotedEvent event = new AllUsersVotedEvent(
        issue.getPlanning().getId(), issue.getId(), issue.getFinishedIterations());
    sseService.sendSseEvent(event);
  }
}
