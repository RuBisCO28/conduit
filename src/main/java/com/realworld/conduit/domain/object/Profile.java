package com.realworld.conduit.domain.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  @JsonIgnore
  private String id;
  private String username;
  private String bio;
  private String image;
  private boolean following;
}
