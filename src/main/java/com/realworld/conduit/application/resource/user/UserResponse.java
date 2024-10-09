package com.realworld.conduit.application.resource.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworld.conduit.domain.object.User;
import javax.annotation.Nonnull;
import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("user")
public class UserResponse {
  private String email;
  private String token;
  private String username;
  private String bio;
  private String image;

  public static UserResponse from(@Nonnull User user, @Nonnull String token) {
    final var response = new UserResponse();
    response.setEmail(user.getEmail());
    response.setToken(token);
    response.setUsername(user.getUsername());
    response.setBio(user.getBio());
    response.setImage(user.getImage());
    return response;
  }
}
