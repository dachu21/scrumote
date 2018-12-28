package com.adach.scrumote.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SseController {

  private final SseService sseService;

  @GetMapping
  public SseEmitter stream() {
    return sseService.addNewEmitter();
  }
}
