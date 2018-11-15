package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.User;
import com.adach.scrumote.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInternalService {

  private final UserRepository repository;

  public List<User> findByIds(Set<Long> userIds) {
    return repository.findAllById(userIds);
  }
}
