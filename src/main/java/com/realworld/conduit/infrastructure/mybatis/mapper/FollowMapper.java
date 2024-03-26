package com.realworld.conduit.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface FollowMapper {

  @SelectProvider(type = FollowSqlBuilder.class, method = "isUserFollowing")
  boolean isUserFollowing(@Param("userId") String userId, @Param("targetUserId") String targetUserId);

  class FollowSqlBuilder implements ProviderMethodResolver {
    public String isUserFollowing() {
      return new SQL()
        .SELECT("count(1)")
        .FROM("follows")
        .WHERE("user_id = #{userId}")
        .WHERE("follow_id = #{targetUserId}")
        .toString();
    }
  }
}
