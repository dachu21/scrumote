package com.adach.scrumote.service.email;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

  public void sendActivationEmail(UserToken userToken, String language) {

  }

  public void sendResetPasswordEmail(UserToken userToken, String language) {

  }
}
