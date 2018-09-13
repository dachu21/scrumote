package com.adach.scrumote.service;

import com.adach.scrumote.entity.User;
import com.adach.scrumote.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private final UserRepository repository;

  public List<User> findAll() {
    return repository.findAll();
  }

  public void save(User entity) {
    repository.save(entity);
  }
}
