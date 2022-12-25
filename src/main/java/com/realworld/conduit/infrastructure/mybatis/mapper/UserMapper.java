package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  User findById(@Param("id") String id);
  void insert(@Param("user") User user);
  User findByEmail(@Param("email") String email);
  User findByName(@Param("name") String name);
  void update(@Param("user") User user);
  FollowRelation findRelation(@Param("userId") String userId, @Param("targetId") String targetId);
  void saveRelation(@Param("followRelation") FollowRelation followRelation);
  void deleteRelation(@Param("followRelation") FollowRelation followRelation);
  List<String> followedUsers(@Param("userId") String userId);
}
