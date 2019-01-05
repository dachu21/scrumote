package com.adach.scrumote.sse;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class SseEvent {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  private static class SseEventMetadata {

    private Instant timestamp;
    private String name;
  }

  private SseEventMetadata metadata;

  protected SseEvent(String name) {
    this.metadata = new SseEventMetadata(Instant.now(), name);
  }
}
