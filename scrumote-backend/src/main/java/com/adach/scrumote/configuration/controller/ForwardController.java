package com.adach.scrumote.configuration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class ForwardController {

  @GetMapping(value = "/**/{[path:[^.]*}")
  public String forward() {
    return "forward:/";
  }
}
