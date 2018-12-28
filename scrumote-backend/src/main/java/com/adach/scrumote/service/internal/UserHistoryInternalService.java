package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.UserHistory;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.repository.UserHistoryRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserHistoryInternalService extends AbstractInternalService<UserHistory> {

  private final UserHistoryRepository repository;

  private final DeckInternalService deckInternalService;

  public void updateUsersHistoryForPlanning(Planning planning) {

    Set<UserHistory> planningUserHistories = new HashSet<>();
    Set<Issue> issues = planning.getIssues();

    for (Issue issue : issues) {
      planningUserHistories.addAll(updateUsersHistoryForIssue(issue));
    }

    for (UserHistory planningUserHistory : planningUserHistories) {
      planningUserHistory.incrementPlannings();
    }
  }

  private Set<UserHistory> updateUsersHistoryForIssue(Issue issue) {

    if (issue.getEstimate().isEmpty()) {
      log.debug(String.format("Issue with id %d is not estimated. Skipping...", issue.getId()));
      return Collections.emptySet();
    }

    Set<UserHistory> issueUserHistories = new HashSet<>();
    Set<Vote> votes = issue.getVotes();
    Map<String, Integer> cardLevelsMap = deckInternalService
        .getCardLevelsMapForDeck(issue.getPlanning().getDeck());
    Integer estimateLevel = cardLevelsMap.get(issue.getEstimate().get());

    for (Vote vote : votes) {
      issueUserHistories.add(updateUsersHistoryForVote(vote, cardLevelsMap, estimateLevel));
    }

    for (UserHistory issueUserHistory : issueUserHistories) {
      issueUserHistory.incrementIssues();
    }

    return issueUserHistories;
  }

  private UserHistory updateUsersHistoryForVote(Vote vote, Map<String, Integer> cardLevelsMap,
      Integer estimateLevel) {
    UserHistory userHistory = vote.getUser().getUserHistory();
    userHistory.incrementVotes();
    if (vote.getIteration().equals(1)) {
      Integer voteLevel = cardLevelsMap.get(vote.getValue());
      userHistory.updateFirstVoteStatistics(voteLevel, estimateLevel);
    }
    return userHistory;
  }
}
