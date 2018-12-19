package com.adach.scrumote.sse;

import java.time.Instant;
import lombok.RequiredArgsConstructor;

public abstract class SseEvent {

  @RequiredArgsConstructor
  private static class SseEventMetadata {

    private final Instant timestamp;
    private final String name;
  }

  private final SseEventMetadata metadata;

  public SseEvent(String name) {
    this.metadata = new SseEventMetadata(Instant.now(), name);
  }
}
