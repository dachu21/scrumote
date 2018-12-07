package com.adach.scrumote.configuration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class CsrfController {

  @PreAuthorize("hasAnyAuthority('swagger')")
  @GetMapping("/csrf")
  public ResponseEntity csrf() {
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }
}