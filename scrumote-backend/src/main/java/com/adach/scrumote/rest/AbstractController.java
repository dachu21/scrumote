package com.adach.scrumote.rest;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class AbstractController {

  protected URI buildLocationUri(Long id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}
