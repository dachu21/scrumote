package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.rest.PrefixedRestController;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.service.external.IssueExternalService;
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
public class IssueController extends AbstractController {

  private final IssueExternalService issueExternalService;

  @PreAuthorize("hasAnyAuthority('createIssue')")
  @PostMapping("/plannings/{planningId}/issues")
  public ResponseEntity<?> createIssue(@PathVariable Long planningId,
      @RequestBody @Valid IssueSimpleDto dto) {
    Long newIssueId = issueExternalService.createIssue(planningId, dto);
    URI location = buildLocationUri(newIssueId);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getIssue')")
  @GetMapping("/plannings/{planningId}/issues/{issueId}")
  public IssueSimpleDto getIssue(@PathVariable Long planningId, @PathVariable Long issueId) {
    return issueExternalService.getIssue(planningId, issueId);
  }

  @PreAuthorize("hasAnyAuthority('getIssuesForPlanning')")
  @GetMapping("/plannings/{planningId}/issues")
  public List<IssueSimpleDto> getIssuesForPlanning(@PathVariable Long planningId) {
    return issueExternalService.getIssuesForPlanning(planningId);
  }

  @PreAuthorize("hasAnyAuthority('updateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{issueId}")
  public ResponseEntity<?> updateIssue(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestBody @Valid IssueSimpleDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    issueExternalService.updateIssue(planningId, issueId, version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('activateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{issueId}/activate")
  public ResponseEntity<?> activateIssue(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    issueExternalService.activateIssue(planningId, issueId, version);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('estimateIssue')")
  @PutMapping("/plannings/{planningId}/issues/{issueId}/estimate")
  public ResponseEntity<?> estimateIssue(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestBody String cardValue, @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    issueExternalService.estimateIssue(planningId, issueId, version, cardValue);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('deleteIssue')")
  @DeleteMapping("/plannings/{planningId}/issues/{issueId}")
  public ResponseEntity<?> deleteIssue(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    issueExternalService.deleteIssue(planningId, issueId, version);
    return ResponseEntity.noContent().build();
  }
}
