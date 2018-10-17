package com.adach.scrumote.configuration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@SuppressWarnings("MVCPathVariableInspection")
public class ForwardController {

  // Not @Secured - used for routing.
  @GetMapping(value = {"/", "/error", "/**/{regex:[^.]*}"})
  public String forward() {
    return "forward:/ui/index.html";
  }
}
