package com.adach.scrumote.service.internal;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserHistoryInternalService {

  private final UserHistoryRepository repository;

  private final CardInternalService cardInternalService;

  public void updateUsersHistoryForPlanning(Planning planning) {

    Set<UserHistory> planningUserHistories = new HashSet<>();
    Set<Issue> issues = planning.getIssues();

    for (Issue issue : issues) {
      planningUserHistories.addAll(updateUsersHistoryForIssue(issue));
    }
    updatePlanningsCountsAndSave(planningUserHistories);
  }

  private Set<UserHistory> updateUsersHistoryForIssue(Issue issue) {

    if (!issue.getEstimate().isPresent()) {
      log.debug(String.format("Issue with id %d is not estimated. Skipping...", issue.getId()));
      return Collections.emptySet();
    }

    Set<UserHistory> issueUserHistories = new HashSet<>();
    Set<Vote> votes = issue.getVotes();
    Map<String, Integer> cardLevelsMap = cardInternalService.getCardLevelsMapForIssue(issue);
    Integer estimateLevel = cardLevelsMap.get(issue.getEstimate().get());

    for (Vote vote : votes) {
      issueUserHistories.add(updateUsersHistoryForVote(vote, cardLevelsMap, estimateLevel));
    }
    updateIssuesCounts(issueUserHistories);

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

  private void updateIssuesCounts(Set<UserHistory> issueUserHistories) {
    for (UserHistory issueUserHistory : issueUserHistories) {
      issueUserHistory.incrementIssues();
    }
  }

  private void updatePlanningsCountsAndSave(Set<UserHistory> planningUserHistories) {
    for (UserHistory planningUserHistory : planningUserHistories) {
      planningUserHistory.incrementPlannings();
      repository.save(planningUserHistory);
    }
  }
}
