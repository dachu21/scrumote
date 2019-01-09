package com.adach.scrumote.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_token_t", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "type"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserToken extends AbstractEntity {

  public enum TokenType {
    ACTIVATION, RESET_PASSWORD
  }

  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private User user;

  //region Data
  @Column(nullable = false, unique = true)
  @NotNull
  private UUID value;

  @Column(nullable = false)
  @NotNull
  private TokenType type;
  //endregion
}
