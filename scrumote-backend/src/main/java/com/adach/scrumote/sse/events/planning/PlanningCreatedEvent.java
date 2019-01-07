package com.adach.scrumote.sse.events.planning;

import com.adach.scrumote.sse.SseEvent;
import lombok.Getter;

@Getter
public class PlanningCreatedEvent extends SseEvent {

  private static final String NAME = "planningCreated";

  public PlanningCreatedEvent() {
    super(NAME);
  }
}
