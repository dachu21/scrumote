package com.adach.scrumote.service.email;

import com.adach.scrumote.entity.UserToken.UserTokenType;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class EmailConstants {

  static final String FROM = "Scrumote";
  static final String EN_LANGUAGE = "en";
  static final String PL_LANGUAGE = "pl";
  static final String ACTIVATION_URL = "activate";
  static final String RESET_PASSWORD_URL = "reset-password";
  static final Map<String, Map<UserTokenType, String>> SUBJECTS_MAP;
  static final Map<String, Map<UserTokenType, String>> TEMPLATES_MAP;

  static {
    SUBJECTS_MAP = new HashMap<>();
    SUBJECTS_MAP.put(EN_LANGUAGE, new HashMap<>());
    SUBJECTS_MAP.put(PL_LANGUAGE, new HashMap<>());
    SUBJECTS_MAP.get(EN_LANGUAGE).put(UserTokenType.ACTIVATION, FROM + " - Account activation");
    SUBJECTS_MAP.get(PL_LANGUAGE).put(UserTokenType.ACTIVATION, FROM + " - Aktywacja konta");
    SUBJECTS_MAP.get(EN_LANGUAGE).put(UserTokenType.RESET_PASSWORD, FROM + " - Resetowanie hasła");
    SUBJECTS_MAP.get(PL_LANGUAGE).put(UserTokenType.RESET_PASSWORD, FROM + " - Password reset");

    TEMPLATES_MAP = new HashMap<>();
    TEMPLATES_MAP.put(EN_LANGUAGE, new HashMap<>());
    TEMPLATES_MAP.put(PL_LANGUAGE, new HashMap<>());
    TEMPLATES_MAP.get(EN_LANGUAGE).put(UserTokenType.ACTIVATION, "activation_en");
    TEMPLATES_MAP.get(PL_LANGUAGE).put(UserTokenType.ACTIVATION, "activation_pl");
    TEMPLATES_MAP.get(EN_LANGUAGE).put(UserTokenType.RESET_PASSWORD, "reset-password_en");
    TEMPLATES_MAP.get(PL_LANGUAGE).put(UserTokenType.RESET_PASSWORD, "reset-password_pl");
  }
}
