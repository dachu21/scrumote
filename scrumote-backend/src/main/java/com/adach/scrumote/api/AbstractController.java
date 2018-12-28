package com.adach.scrumote.api;

import com.adach.scrumote.exception.optimisticlock.InvalidVersionHeaderException;
import com.adach.scrumote.exception.optimisticlock.MissingVersionHeaderException;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class AbstractController {

  final String VERSION_HEADER = "If-Match";

  URI buildLocationUri(Long id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }

  Long extractVersion(String versionHeader) {
    if (versionHeader == null || versionHeader.isEmpty()) {
      throw new MissingVersionHeaderException("Invalid request: Version header is missing");
    }
    try {
      versionHeader = versionHeader.replace("\"", "");
      return Long.valueOf(versionHeader);
    } catch (NumberFormatException e) {
      throw new InvalidVersionHeaderException(
          "Invalid request: Version header could not be interpreted as number.", e);
    }
  }
}
