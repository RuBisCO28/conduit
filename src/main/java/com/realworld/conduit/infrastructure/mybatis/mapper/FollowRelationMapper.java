package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.FollowRelation;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface FollowRelationMapper {

  @SelectProvider(type = FollowRelationSqlBuilder.class, method = "findRelation")
  FollowRelation findRelation(@Param("userId") String userId, @Param("targetId") String targetId);

  @InsertProvider(type = FollowRelationSqlBuilder.class, method = "saveRelation")
  void saveRelation(@Param("followRelation") FollowRelation followRelation);

  @DeleteProvider(type = FollowRelationSqlBuilder.class, method = "deleteRelation")
  void deleteRelation(@Param("followRelation") FollowRelation followRelation);

  @SelectProvider(type = FollowRelationSqlBuilder.class, method = "followedUsers")
  List<FollowRelation> followedUsers(@Param("userId") String userId);

  class FollowRelationSqlBuilder implements ProviderMethodResolver {
    public String findRelation() {
      return new SQL()
        .SELECT("user_id as userId, follow_id as targetId")
        .FROM("follows")
        .WHERE("user_id = #{userId} and follow_id = #{targetId}")
        .toString();
    }

    public String saveRelation() {
      return new SQL()
        .INSERT_INTO("follows")
        .VALUES("user_id", "#{followRelation.userId}")
        .VALUES("follow_id", "#{followRelation.targetId}")
        .toString();
    }

    public String deleteRelation() {
      return new SQL()
        .DELETE_FROM("follows")
        .WHERE("user_id = #{followRelation.userId} and follow_id = #{followRelation.targetId}")
        .toString();
    }

    public String followedUsers() {
      return new SQL()
        .SELECT("user_id as userId, follow_id as targetId")
        .FROM("follows")
        .WHERE("user_id = #{userId}")
        .toString();
    }
  }
}
