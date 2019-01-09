package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserToken;
import com.adach.scrumote.entity.UserToken.TokenType;
import com.adach.scrumote.exception.usertoken.UserTokenNotFoundException;
import com.adach.scrumote.repository.UserTokenRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTokenInternalService extends AbstractInternalService<UserToken> {

  private final UserTokenRepository repository;

  //region Repository methods calls
  public UserToken save(UserToken userToken) {
    return repository.save(userToken);
  }

  public UserToken findByValue(UUID value) {
    return repository
        .findByValue(value)
        .orElseThrow(() -> new UserTokenNotFoundException(
            String.format("User token with value %s does not exist.", value)));
  }

  public UserToken findByValueAndType(UUID value, TokenType tokenType) {
    return repository
        .findByValueAndType(value, tokenType)
        .orElseThrow(() -> new UserTokenNotFoundException(
            String.format("User token with value %s and type %s does not exist.", value,
                tokenType.name())));
  }

  public UserToken findByUserAndType(User user, TokenType tokenType) {
    return repository
        .findByUserAndType(user, tokenType)
        .orElseThrow(() -> new UserTokenNotFoundException(
            String.format("User token for user %d with type %s does not exist.", user.getId(),
                tokenType.name())));
  }

  public boolean existsByUserAndType(User user, TokenType tokenType) {
    return repository.existsByUserAndType(user, tokenType);
  }
  //endregion

  public UserToken saveOrUpdateToken(User user, TokenType tokenType) {
    UserToken token;
    if (this.existsByUserAndType(user, tokenType)) {
      token = this.findByUserAndType(user, tokenType);
      token.setValue(UUID.randomUUID());
    } else {
      token = this.save(new UserToken(user, UUID.randomUUID(), tokenType));
    }
    return token;
  }
}
