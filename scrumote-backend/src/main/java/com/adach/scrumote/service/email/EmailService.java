package com.adach.scrumote.service.email;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.entity.UserToken;
import com.adach.scrumote.entity.UserToken.UserTokenType;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Async
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EmailService {

  @Value("${custom.application.url}")
  private String APPLICATION_URL;

  @Value("${custom.mail.address}")
  private String EMAIL_ADDRESS;

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @PostConstruct
  void postConstruct() {
    if (APPLICATION_URL.endsWith("/")) {
      APPLICATION_URL = APPLICATION_URL.substring(0, APPLICATION_URL.length() - 1);
    }
  }

  public void sendUserTokenEmail(UserToken userToken, String requestLanguage) {
    String verifiedLanguage = verifyAndSetLanguage(requestLanguage);

    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom(EMAIL_ADDRESS, EmailConstants.FROM);
      messageHelper.setTo(userToken.getUser().getEmail());
      messageHelper
          .setSubject(EmailConstants.SUBJECTS_MAP.get(verifiedLanguage).get(userToken.getType()));
      String content = buildHtmlContent(userToken, verifiedLanguage);
      messageHelper.setText(content, true);
    };
    sendMessage(mimeMessagePreparator);
  }

  private String verifyAndSetLanguage(String requestLanguage) {
    if (!requestLanguage.equals(EmailConstants.EN_LANGUAGE) &&
        !requestLanguage.equals(EmailConstants.PL_LANGUAGE)) {
      return EmailConstants.EN_LANGUAGE;
    }
    return requestLanguage;
  }

  private String buildHtmlContent(UserToken userToken, String verifiedLanguage) {
    Context context = new Context();
    context.setVariable("username", userToken.getUser().getUsername());
    context.setVariable("url", buildUserTokenUrl(userToken));
    return templateEngine.process(
        EmailConstants.TEMPLATES_MAP.get(verifiedLanguage).get(userToken.getType()), context);
  }

  private String buildUserTokenUrl(UserToken userToken) {
    String url = "";
    if (userToken.getType().equals(UserTokenType.ACTIVATION)) {
      url = APPLICATION_URL + EmailConstants.ACTIVATION_URL + "/" + userToken.getValue();
    } else if (userToken.getType().equals(UserTokenType.RESET_PASSWORD)) {
      url = APPLICATION_URL + EmailConstants.RESET_PASSWORD_URL + "/" + userToken.getValue();
    }
    return url;
  }

  protected void sendMessage(MimeMessagePreparator mimeMessagePreparator) {
    try {
      mailSender.send(mimeMessagePreparator);
    } catch (MailException e) {
      log.error(e.getMessage(), e);
    }
  }
}
