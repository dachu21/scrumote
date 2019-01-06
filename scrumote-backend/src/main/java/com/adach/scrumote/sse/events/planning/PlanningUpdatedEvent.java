package com.adach.scrumote.sse.events.planning;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanningUpdatedEvent extends SseEvent {

  private static final String NAME = "planningUpdated";

  private Long planningId;

  public PlanningUpdatedEvent(Long planningId) {
    super(NAME);
    this.planningId = planningId;
  }
}
