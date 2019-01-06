package com.adach.scrumote.sse.events.issue;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCreatedEvent extends SseEvent {

  private static final String NAME = "issueCreated";

  private Long planningId;

  public IssueCreatedEvent(Long planningId) {
    super(NAME);
    this.planningId = planningId;
  }
}
