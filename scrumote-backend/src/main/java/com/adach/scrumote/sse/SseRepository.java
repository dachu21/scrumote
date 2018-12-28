package com.adach.scrumote.sse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseRepository {

  private final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());

  Iterable<SseEmitter> getEmitters() {
    return emitters;
  }

  void addEmitter(SseEmitter emitter) {
    emitters.add(emitter);
  }

  void removeEmitter(SseEmitter emitter) {
    emitters.remove(emitter);
  }
}
