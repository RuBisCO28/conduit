package com.realworld.conduit.application.resource.profile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworld.conduit.domain.object.Profile;
import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("profile")
public class ProfileResponse {
  private String username;
  private String bio;
  private String image;
  private boolean following;

  public static ProfileResponse from(Profile profile) {
    final var response = new ProfileResponse();
    response.setUsername(profile.getUsername());
    response.setBio(profile.getBio());
    response.setImage(profile.getImage());
    response.setFollowing(profile.isFollowing());
    return response;
  }
}
