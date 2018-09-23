package com.adach.scrumote.dto.simple;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSimpleDto extends AbstractSimpleDto {

  private Long id;

  @NotNull
  @Size(min = 3, max = 32)
  private String username;

  @NotNull
  @Size(min = 8, max = 64)
  private String password;

  @NotNull
  @Email
  private String email;

  @Size(max = 32)
  private String firstName;

  @Size(max = 32)
  private String lastName;
}
