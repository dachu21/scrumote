package com.adach.scrumote.dto.complex;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithPasswordDto {

  @NotNull
  private UserSimpleDto user;

  @NotNull
  private PasswordDto password;
}
