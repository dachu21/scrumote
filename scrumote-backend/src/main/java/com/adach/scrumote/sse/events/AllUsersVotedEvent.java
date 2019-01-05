package com.adach.scrumote.sse.events;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllUsersVotedEvent extends SseEvent {

  private static final String NAME = "allUsersVoted";

  private Long planningId;
  private Long issueId;
  private Integer iteration;

  public AllUsersVotedEvent(Long planningId, Long issueId, Integer iteration) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
    this.iteration = iteration;
  }
}
