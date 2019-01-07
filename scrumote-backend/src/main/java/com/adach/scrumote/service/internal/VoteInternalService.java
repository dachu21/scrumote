package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.exception.vote.VoteNotFoundException;
import com.adach.scrumote.repository.VoteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteInternalService extends AbstractInternalService<Vote> {

  private final VoteRepository repository;

  //region Repository methods calls
  public Vote save(Vote vote) {
    return repository.save(vote);
  }

  public List<Vote> findAllByIssueAndIteration(Issue issue, Integer iteration) {
    return repository.findAllByIssueAndIteration(issue, iteration);
  }

  public List<Vote> findAllByIssueExcludingIteration(Issue issue, Integer iteration) {
    return repository.findAllByIssueAndIterationNot(issue, iteration);
  }

  public Vote findById(Long id) {
    Optional<Vote> voteOpt = repository.findById(id);
    return voteOpt.orElseThrow(
        () -> new VoteNotFoundException(String.format("Vote with id %d does not exist.", id)));
  }

  public boolean checkIfVoteExists(Issue issue, Integer iteration, User currentUser) {
    return repository.existsByIssueAndIterationAndUser(issue, iteration, currentUser);
  }
  //endregion
}
