package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.rest.PrefixedRestController;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.service.external.PlanningExternalService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningController extends AbstractController {

  private final PlanningExternalService planningExternalService;

  @PreAuthorize("hasAnyAuthority('createPlanning')")
  @PostMapping("/plannings")
  public ResponseEntity<?> createPlanningWithUsers(@RequestBody @Valid PlanningSimpleDto dto) {
    Long newPlanningId = planningExternalService.createPlanning(dto);
    URI location = buildLocationUri(newPlanningId);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getAnyPlanning', 'getMyPlanning')")
  @GetMapping("/plannings/{planningId}")
  public PlanningSimpleDto getPlanning(@PathVariable Long planningId) {
    return planningExternalService.getPlanning(planningId);
  }

  @PreAuthorize("hasAnyAuthority('getAllPlannings')")
  @GetMapping("/plannings")
  public List<PlanningSimpleDto> getAllPlannings() {
    return planningExternalService.getAllPlannings();
  }

  @PreAuthorize("hasAnyAuthority('getMyPlannings')")
  @GetMapping("/plannings/my")
  public List<PlanningSimpleDto> getMyPlannings() {
    return planningExternalService.getMyPlannings();
  }

  @PreAuthorize("hasAnyAuthority('updatePlanning')")
  @PutMapping("/plannings/{planningId}")
  public ResponseEntity<?> updatePlanning(@PathVariable Long planningId,
      @RequestBody @Valid PlanningSimpleDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    planningExternalService.updatePlanning(planningId, version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('finishPlanning')")
  @PutMapping("/plannings/{planningId}/finish")
  public ResponseEntity<?> finishPlanning(@PathVariable Long planningId,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    planningExternalService.finishPlanning(planningId, version);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('deletePlanning')")
  @DeleteMapping("/plannings/{planningId}")
  public ResponseEntity<?> deletePlanning(@PathVariable Long planningId,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    planningExternalService.deletePlanning(planningId, version);
    return ResponseEntity.noContent().build();
  }
}
