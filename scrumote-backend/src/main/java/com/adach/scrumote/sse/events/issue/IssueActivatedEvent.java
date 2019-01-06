package com.adach.scrumote.sse.events.issue;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueActivatedEvent extends SseEvent {

  private static final String NAME = "issueActivated";

  private Long planningId;
  private Long issueId;

  public IssueActivatedEvent(Long planningId, Long issueId) {
    super(NAME);
    this.planningId = planningId;
    this.issueId = issueId;
  }
}
