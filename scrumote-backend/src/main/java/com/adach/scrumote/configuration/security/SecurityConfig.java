package com.adach.scrumote.configuration.security;

import com.adach.scrumote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(userRepository);
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPointImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.httpBasic();

    http.requiresChannel().anyRequest().requiresSecure();

    http.csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringAntMatchers(AntMatchers.API_ANONYMOUS);

    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

    http.authorizeRequests()
        .antMatchers(AntMatchers.ROUTES_PERMIT_ALL).permitAll()
        .antMatchers(AntMatchers.ROUTES_ANONYMOUS).hasRole("ANONYMOUS")
        .antMatchers(AntMatchers.API_ANONYMOUS).hasRole("ANONYMOUS")
        .antMatchers(AntMatchers.API_ANONYMOUS).hasAuthority("swagger")
        .antMatchers(AntMatchers.SWAGGER).hasAuthority("swagger")
        .anyRequest().authenticated();
  }
}
