package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.infrastructure.mybatis.mapper.UserMapper.UserSqlBuilder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface FollowRelation {

  @SelectProvider(type = UserSqlBuilder.class, method = "findRelation")
  com.realworld.conduit.domain.object.FollowRelation findRelation(@Param("userId") String userId, @Param("targetId") String targetId);

  @InsertProvider(type = UserSqlBuilder.class, method = "saveRelation")
  void saveRelation(@Param("followRelation") com.realworld.conduit.domain.object.FollowRelation followRelation);

  void deleteRelation(@Param("followRelation") com.realworld.conduit.domain.object.FollowRelation followRelation);
}
