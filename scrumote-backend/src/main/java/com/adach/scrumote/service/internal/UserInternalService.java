package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInternalService {

  private final UserRepository repository;

  public User save(User user) {
    return repository.save(user);
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public List<User> findByIds(Set<Long> userIds) {
    return repository.findAllById(userIds);
  }
}
