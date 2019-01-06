package com.adach.scrumote.sse.events.issue;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllUsersVotedEvent extends SseEvent {

  private static final String NAME = "allUsersVoted";

  private Long planningId;
  private Long issueId;

  public AllUsersVotedEvent(Long planningId, Long issueId) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
  }
}
