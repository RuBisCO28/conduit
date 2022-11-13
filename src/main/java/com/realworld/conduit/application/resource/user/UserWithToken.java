package com.realworld.conduit.application.resource.user;

import com.realworld.conduit.domain.object.User;
import lombok.Getter;

@Getter
public class UserWithToken {
  private String email;
  private String username;
  private String bio;
  private String image;
  private String token;

  public UserWithToken(User user, String token) {
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.bio = user.getBio();
    this.image = user.getImage();
    this.token = token;
  }
}
