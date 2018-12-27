package com.adach.scrumote.dto.complex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

  private String oldPassword;

  @NotNull
  @Size(min = 8, max = 64)
  private String newPassword;
}
