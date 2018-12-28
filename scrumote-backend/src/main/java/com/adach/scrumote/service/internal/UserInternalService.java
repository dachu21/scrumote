package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.exception.user.UserNotFoundException;
import com.adach.scrumote.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInternalService extends AbstractInternalService<User> {

  private final UserRepository repository;

  //region Repository methods calls
  public User save(User user) {
    return repository.save(user);
  }

  public User findById(Long id) {
    Optional<User> userOpt = repository.findById(id);
    return userOpt.orElseThrow(() -> new UserNotFoundException(
        String.format("User with id %d does not exist.", id)));
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public List<User> findByIds(Set<Long> userIds) {
    return repository.findAllById(userIds);
  }
  //endregion
}
