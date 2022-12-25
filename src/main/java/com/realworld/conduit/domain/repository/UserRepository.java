package com.realworld.conduit.domain.repository;

import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.User;
import java.util.List;

public interface UserRepository {
  void save(User user);
  User findByEmail(String email);

  User findByName(String name);
  User findById(String id);
  void update(User user);
  FollowRelation findRelation(String userId, String targetId);
  void saveRelation(FollowRelation relation);
  boolean isFollowing(String userId, String targetUserId);
  void deleteRelation(FollowRelation relation);
  List<String> followedUsers(String userId);
}
