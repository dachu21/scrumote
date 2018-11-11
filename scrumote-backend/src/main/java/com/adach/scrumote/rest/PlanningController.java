package com.adach.scrumote.rest;

import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.service.external.PlanningPublicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningController {

  private final PlanningPublicService planningPublicService;

  @PutMapping("/planning/finish")
  public void finishPlanning(@RequestBody Long id) {
    planningPublicService.finish(id);
  }

  @GetMapping("/plannings")
  public List<PlanningSimpleDto> getPlannings() {
    return planningPublicService.findAll();
  }
}
