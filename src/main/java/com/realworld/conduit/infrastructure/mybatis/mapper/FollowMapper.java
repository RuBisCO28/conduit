package com.realworld.conduit.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {
  boolean isUserFollowing(@Param("userId") String userId, @Param("targetUserId") String targetUserId);
}
