package com.realworld.conduit.domain.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  private String id;
  private String username;
  private String bio;
  private String image;
  private boolean following;
}
