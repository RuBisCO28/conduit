package com.realworld.conduit.domain.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
  private String username;
  private String bio;
  private String image;
  private boolean following;

  public Author(Profile profile) {
    this.username = profile.getUsername();
    this.bio = profile.getBio();
    this.image = profile.getImage();
    this.following = profile.isFollowing();
  }
}
