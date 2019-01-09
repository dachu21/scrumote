package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.exception.user.EmailAlreadyExistsException;
import com.adach.scrumote.exception.user.UserNotFoundException;
import com.adach.scrumote.exception.user.UsernameAlreadyExistsException;
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
    return repository.findAll(ID_DESC_SORT);
  }

  public List<User> findAllByRole(Role role) {
    return repository.findAllByRolesContains(role, ID_DESC_SORT);
  }

  public List<User> findByIds(Set<Long> userIds) {
    return repository.findAllById(userIds);
  }

  public User findByEmail(String email) {
    Optional<User> userOpt = repository.findByEmail(email);
    return userOpt.orElseThrow(() -> new UserNotFoundException(
        String.format("User with email %s does not exist.", email)));
  }
  //endregion

  //region Validation methods
  public void validateUsernameNotExists(String username) {
    if (repository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException(
          String.format("User with username %s already exists.", username));
    }
  }

  public void validateEmailNotExists(String email) {
    if (repository.existsByEmail(email)) {
      throw new EmailAlreadyExistsException(
          String.format("User with email %s already exists.", email));
    }
  }
  //endregion
}
