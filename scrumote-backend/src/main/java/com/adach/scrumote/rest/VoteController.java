package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.rest.PrefixedRestController;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.service.external.IssueExternalService;
import com.adach.scrumote.service.external.VoteExternalService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteController extends AbstractController {

  private final VoteExternalService voteExternalService;
  private final IssueExternalService issueExternalService;

  @PreAuthorize("hasAnyAuthority('createVote')")
  @PostMapping("/plannings/{planningId}/issues/{issueId}/votes")
  public ResponseEntity<?> createVote(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestBody VoteSimpleDto dto) {
    Long id = voteExternalService.createVote(planningId, issueId, dto);
    issueExternalService.deactivateIssueIfAllUsersVoted(issueId, id);
    URI location = buildLocationUri(id);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getVotesForIssue')")
  @GetMapping("/plannings/{planningId}/issues/{issueId}/votes")
  public List<VoteSimpleDto> getVotesForIssue(@PathVariable Long planningId,
      @PathVariable Long issueId, @RequestParam Integer iteration) {
    return voteExternalService.getVotesForIssue(planningId, issueId, iteration);
  }
}
