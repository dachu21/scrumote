package com.adach.scrumote.service.security;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.exception.password.OldPasswordNotValidException;
import com.adach.scrumote.exception.password.PasswordDoesNotSatisfyConditions;
import com.adach.scrumote.exception.password.PasswordSameAsOldException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PasswordService {

  private static final Integer MIN_LENGTH = 8;
  private static final Integer MAX_LENGTH = 8;
  private static final boolean UPPERCASE_REQUIRED = true;
  private static final boolean NUMBER_REQUIRED = true;

  private final PasswordEncoder passwordEncoder;

  public String encode(String password) {
    return passwordEncoder.encode(password);
  }

  public void validateEqualToOldPassword(String password, User user) {
    if (!encode(password).equals(user.getPassword())) {
      throw new OldPasswordNotValidException(
          String.format("Provided old password for user with id %d is not valid.", user.getId()));
    }
  }

  public void validateNotEqualToOldPassword(String password, User user) {
    if (encode(password).equals(user.getPassword())) {
      throw new PasswordSameAsOldException(
          String.format("Provided password for user with id %d is same as old.", user.getId()));
    }
  }

  public void validateSatisfiesConditions(String password) {
    if (password.length() < MIN_LENGTH ||
        password.length() > MAX_LENGTH ||
        (UPPERCASE_REQUIRED && containsUppercase(password)) ||
        (NUMBER_REQUIRED && containsNumber(password))) {
      throw new PasswordDoesNotSatisfyConditions("Provided password does not satisfy conditions");
    }
  }

  private boolean containsUppercase(String password) {
    return password.chars().anyMatch(i -> Character.isLetter(i) && Character.isUpperCase(i));
  }

  private boolean containsNumber(String password) {
    return password.chars().anyMatch(Character::isDigit);
  }
}
