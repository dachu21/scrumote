package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserToken;
import com.adach.scrumote.entity.UserToken.TokenType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface UserTokenRepository extends CustomJpaRepository<UserToken, Long> {

  Optional<UserToken> findByValue(UUID value);

  Optional<UserToken> findByValueAndType(UUID value, TokenType tokenType);

  Optional<UserToken> findByUserAndType(User user, TokenType tokenType);

  boolean existsByUserAndType(User user, TokenType tokenType);
}
