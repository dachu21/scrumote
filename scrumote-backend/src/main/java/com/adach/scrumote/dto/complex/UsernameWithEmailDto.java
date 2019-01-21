package com.adach.scrumote.dto.complex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsernameWithEmailDto {

  @NotNull
  private String username;

  @NotNull
  @Email
  private String email;
}
