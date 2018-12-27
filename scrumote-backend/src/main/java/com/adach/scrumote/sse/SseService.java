package com.adach.scrumote.sse;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SseService {

  private final SseRepository sseRepository;

  SseEmitter addNewEmitter() {
    SseEmitter emitter = new SseEmitter();
    sseRepository.addEmitter(emitter);
    emitter.onCompletion(() -> sseRepository.removeEmitter(emitter));
    return emitter;
  }

  public void sendSseEvent(SseEvent sseEvent) {
    sseRepository.getEmitters().forEach(emitter -> {
      try {
        emitter.send(sseEvent, MediaType.APPLICATION_JSON);
      } catch (IOException e) {
        emitter.complete();
        log.error(e.getMessage(), e);
      }
    });
  }
}
