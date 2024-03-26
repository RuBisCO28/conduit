package com.realworld.conduit.domain.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRelation {
  private String userId;
  private String targetId;
}
