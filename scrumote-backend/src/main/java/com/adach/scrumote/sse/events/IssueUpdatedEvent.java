package com.adach.scrumote.sse.events;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueUpdatedEvent extends SseEvent {

  private static final String NAME = "issueUpdated";

  private Long planningId;
  private Long issueId;

  public IssueUpdatedEvent(Long planningId, Long issueId) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
  }
}
