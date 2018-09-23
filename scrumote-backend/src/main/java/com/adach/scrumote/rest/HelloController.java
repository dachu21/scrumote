package com.adach.scrumote.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;

@PrefixedRestController
public class HelloController {

  @GetMapping("/hello")
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World");
    return model;
  }
}
