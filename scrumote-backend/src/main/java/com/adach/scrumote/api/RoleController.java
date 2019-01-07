package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.simple.RoleSimpleDto;
import com.adach.scrumote.service.external.RoleExternalService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController extends AbstractController {

  private final RoleExternalService roleExternalService;

  @PreAuthorize("hasAnyAuthority('getAllRoles')")
  @GetMapping("/roles")
  public List<RoleSimpleDto> getAllRoles() {
    return roleExternalService.getAllRoles();
  }
}
