package com.adach.scrumote.sse.events;

import com.adach.scrumote.sse.SseEvent;

public class AllUsersVotedEvent extends SseEvent {

  private static final String NAME = "allUsersVotedEvent";

  private final Long planningId;
  private final Long issueId;
  private final Integer iteration;

  public AllUsersVotedEvent(Long planningId, Long issueId, Integer iteration) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
    this.iteration = iteration;
  }
}
