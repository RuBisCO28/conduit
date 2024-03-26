package com.realworld.conduit.application.resource.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonRootName(value = "user")
@Builder
public class UpdateUserRequest {

  @Builder.Default
  @Email(message = "should be an email")
  @DuplicatedEmailConstraint
  private String email = "";

  @Builder.Default
  @DuplicatedUsernameConstraint
  private String username = "";

  @Builder.Default private String password = "";
  @Builder.Default private String bio = "";
  @Builder.Default private String image = "";
}
