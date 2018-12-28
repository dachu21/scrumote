package com.adach.scrumote.configuration.security;

class AntMatchers {

  private static final String API_PREFIX = "/api";

  static final String[] ROUTES_PERMIT_ALL = {
      "/ui/**"
  };

  static final String[] ROUTES_ANONYMOUS = { //TODO chyba sie tego pozbyc i postawic na angulara
      "/login", "/register"
  };

  static final String[] API_ANONYMOUS = {
      API_PREFIX + "/users/register",
      API_PREFIX + "/system-features/*"
  };

  static final String[] SWAGGER = {
      "/swagger-ui.html", "/v2/api-docs", "/csrf"
  };
}
