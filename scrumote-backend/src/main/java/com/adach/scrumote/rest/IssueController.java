package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.controller.PrefixedRestController;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.service.external.IssueExternalService;
import java.net.URI;
import java.util.List;
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

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueController extends AbstractController {

  private final IssueExternalService issueExternalService;

  @PreAuthorize("hasAnyAuthority('createIssue')")
  @PostMapping("/plannings/{planningId}/issues")
  public ResponseEntity<?> createIssue(@PathVariable Long planningId,
      @RequestBody IssueSimpleDto dto) {
    Long id = issueExternalService.createIssue(planningId, dto);
    URI location = buildLocationUri(id);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getIssue')")
  @GetMapping("/plannings/{planningId}/issues/{id}")
  public IssueSimpleDto getIssue(@PathVariable Long planningId, @PathVariable Long id) {
    return issueExternalService.getIssue(planningId, id);
  }

  @PreAuthorize("hasAnyAuthority('getIssuesForPlanning')")
  @GetMapping("/plannings/{planningId}/issues")
  public List<IssueSimpleDto> getIssuesForPlanning(@PathVariable Long planningId) {
    return issueExternalService.getIssuesForPlanning(planningId);
  }

  @PreAuthorize("hasAnyAuthority('updateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{id}")
  public ResponseEntity<?> updateIssue(@PathVariable Long planningId, @PathVariable Long id,
      @RequestBody IssueSimpleDto dto) {
    issueExternalService.updateIssue(planningId, id, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('activateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{id}/activate")
  public ResponseEntity<?> activateIssue(@PathVariable Long planningId, @PathVariable Long id) {
    issueExternalService.activateIssue(planningId, id);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('estimateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{id}/estimate")
  public ResponseEntity<?> estimateIssue(@PathVariable Long planningId, @PathVariable Long id,
      @RequestBody String cardValue) {
    issueExternalService.estimateIssue(planningId, id, cardValue);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('deleteIssue')")
  @DeleteMapping("/plannings/{planningId}/issues/{id}")
  public ResponseEntity<?> deleteIssue(@PathVariable Long planningId, @PathVariable Long id) {
    issueExternalService.deleteIssue(planningId, id);
    return ResponseEntity.noContent().build();
  }
}
