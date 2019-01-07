package com.adach.scrumote.sse.events.planning;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanningFinishedEvent extends SseEvent {

  private static final String NAME = "planningFinished";

  private Long planningId;

  public PlanningFinishedEvent(Long planningId) {
    super(NAME);
    this.planningId = planningId;
  }
}
