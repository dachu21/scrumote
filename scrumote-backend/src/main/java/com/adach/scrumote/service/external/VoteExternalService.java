package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.mapper.VoteMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.IssueInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.VoteInternalService;
import com.adach.scrumote.service.security.SessionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteExternalService {

  private final VoteInternalService internalService;
  private final VoteMapper mapper;

  private final IssueInternalService issueInternalService;
  private final DeckInternalService deckInternalService;
  private final PlanningInternalService planningInternalService;

  private final SessionService sessionService;

  @PreAuthorize("hasAnyAuthority('createVote')")
  public Long createVote(Long planningId, Long issueId, VoteSimpleDto dto) {
    Issue issue = issueInternalService.findById(issueId);
    Planning planning = issue.getPlanning();
    Deck deck = planning.getDeck();

    issueInternalService.validateBelongsToPlanningWithId(issue, planningId);
    issueInternalService.validateIsCurrentlyActiveIteration(issue, dto.getIteration());
    planningInternalService.validateContainsCurrentUser(planning);
    deckInternalService.validateContainsCardWithValue(deck, dto.getValue());

    Vote vote = mapper.mapToEntity(dto);
    vote.setIssue(issue);
    vote.setUser(sessionService.getCurrentUser());

    return internalService.save(vote).getId();
  }

  @PreAuthorize("hasAnyAuthority('getVotesForIssue')")
  public List<VoteSimpleDto> getAllVotesForIssue(Long planningId, Long issueId) {
    Issue issue = issueInternalService.findById(issueId);
    Planning planning = issue.getPlanning();

    issueInternalService.validateBelongsToPlanningWithId(issue, planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    Integer currentIteration = issue.getFinishedIterations() + 1;
    return internalService.findAllByIssueExcludingIteration(issue, currentIteration).stream()
        .map(mapper::mapToSimpleDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('getVotesForIssue')")
  public List<VoteSimpleDto> getVotesForIssueAndIteration(Long planningId, Long issueId,
      Integer iteration) {
    Issue issue = issueInternalService.findById(issueId);
    Planning planning = issue.getPlanning();

    issueInternalService.validateBelongsToPlanningWithId(issue, planningId);
    issueInternalService.validateIsNotCurrentlyActiveIteration(issue, iteration);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return internalService.findAllByIssueAndIteration(issue, iteration).stream()
        .map(mapper::mapToSimpleDto).collect(Collectors.toList());
  }
}
