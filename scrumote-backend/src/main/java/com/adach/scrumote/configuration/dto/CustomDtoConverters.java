package com.adach.scrumote.configuration.dto;

import com.adach.scrumote.entity.Card;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Permission;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.SystemFeature;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserStats;
import com.adach.scrumote.entity.Vote;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CustomDtoConverters {

  static final Converter<Optional<String>, String> fromOptionalToString =
      new AbstractConverter<>() {
        @Override
        protected String convert(Optional<String> optional) {
          return optional.orElse(null);
        }
      };

  static final Converter<String, Optional<String>> fromStringToOptional =
      new AbstractConverter<>() {
        @Override
        protected Optional<String> convert(String string) {
          return Optional.ofNullable(string);
        }
      };

  static final Converter<Card, Long> fromCardToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Card entity) {
          return entity.getId();
        }
      };

  static final Converter<Deck, Long> fromDeckToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Deck entity) {
          return entity.getId();
        }
      };

  static final Converter<Issue, Long> fromIssueToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Issue entity) {
          return entity.getId();
        }
      };

  static final Converter<Permission, Long> fromPermissionToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Permission entity) {
          return entity.getId();
        }
      };

  static final Converter<Planning, Long> fromPlanningToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Planning entity) {
          return entity.getId();
        }
      };

  static final Converter<Role, Long> fromRoleToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Role entity) {
          return entity.getId();
        }
      };

  static final Converter<UserStats, Long> fromUserStatsToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(UserStats entity) {
          return entity.getId();
        }
      };

  static final Converter<User, Long> fromUserToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(User entity) {
          return entity.getId();
        }
      };

  static final Converter<Vote, Long> fromVoteToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(Vote entity) {
          return entity.getId();
        }
      };

  static final Converter<SystemFeature, Long> fromSystemFeatureToLong =
      new AbstractConverter<>() {
        @Override
        protected Long convert(SystemFeature entity) {
          return entity.getId();
        }
      };

}
