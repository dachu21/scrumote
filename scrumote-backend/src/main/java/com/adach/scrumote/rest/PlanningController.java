package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.controller.PrefixedRestController;
import com.adach.scrumote.dto.complex.PlanningWithUsersDto;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.service.external.PlanningExternalService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningController extends AbstractController {

  private final PlanningExternalService planningExternalService;

  @GetMapping("/planning/{id}")
  public PlanningWithUsersDto getPlanningWithUsers(@PathVariable Long id) {
    return planningExternalService.findById(id);
  }

  @GetMapping("/planning")
  public List<PlanningSimpleDto> getAllPlannings() {
    return planningExternalService.findAll();
  }

  @PostMapping("/planning")
  public ResponseEntity<?> createPlanningWithUsers(@RequestBody PlanningWithUsersDto dto) {
    Long id = planningExternalService.create(dto);
    URI location = buildLocationUri(id);
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/planning/{id}")
  public ResponseEntity<?> updatePlanning(@PathVariable Long id,
      @RequestBody PlanningWithUsersDto dto) {
    planningExternalService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/planning/{id}/finish")
  public ResponseEntity<?> finishPlanning(@PathVariable Long id) {
    planningExternalService.finish(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/planning/{id}")
  public ResponseEntity<?> deletePlanning(@PathVariable Long id) {
    planningExternalService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
