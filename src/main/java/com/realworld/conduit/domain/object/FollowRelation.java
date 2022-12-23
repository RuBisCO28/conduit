package com.realworld.conduit.domain.object;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FollowRelation {
  private String userId;
  private String targetId;

  public FollowRelation(String userId, String targetId) {

    this.userId = userId;
    this.targetId = targetId;
  }
}
