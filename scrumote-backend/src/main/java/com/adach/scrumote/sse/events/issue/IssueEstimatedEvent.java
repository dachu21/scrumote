package com.adach.scrumote.sse.events.issue;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueEstimatedEvent extends SseEvent {

  private static final String NAME = "issueEstimated";

  private Long planningId;
  private Long issueId;

  public IssueEstimatedEvent(Long planningId, Long issueId) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
  }
}
