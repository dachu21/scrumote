package com.adach.scrumote.sse.events.planning;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanningDeletedEvent extends SseEvent {

  private static final String NAME = "planningDeleted";

  private Long planningId;

  public PlanningDeletedEvent(Long planningId) {
    super(NAME);
    this.planningId = planningId;
  }
}
