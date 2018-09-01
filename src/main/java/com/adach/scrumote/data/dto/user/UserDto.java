package com.adach.scrumote.data.dto.user;

import com.adach.scrumote.data.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

  private Long id;
  private String login;
  private String password;
}
