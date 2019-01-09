package com.adach.scrumote.configuration.security;

class AntMatchers {

  private static final String API_PREFIX = "/api";

  static final String[] CSRF_IGNORE = {
      API_PREFIX + "/users/register",
      API_PREFIX + "/user-tokens/**",
  };

  static final String[] PERMIT_ALL = {
      "/",
      "/ui/**",
      "/login",
      "/register",
      "/activate",
      "/reset-password",
      API_PREFIX + "/users/register",
      API_PREFIX + "/system-features/*",
      API_PREFIX + "/user-tokens/**"
  };

  static final String[] SWAGGER = {
      "/swagger-ui.html",
      "/v2/api-docs",
      "/csrf"
  };
}
