package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface UserMapper {
  @SelectProvider(type = UserSqlBuilder.class, method = "findByEmail")
  User findByEmail(@Param("email") String email);

  @SelectProvider(type = UserSqlBuilder.class, method = "findByName")
  User findByName(@Param("name") String name);

  @InsertProvider(type = UserSqlBuilder.class, method = "insert")
  void insert(@Param("user") User user);

  @SelectProvider(type = UserSqlBuilder.class, method = "findById")
  User findById(@Param("id") String id);

  @UpdateProvider(type = UserSqlBuilder.class, method = "update")
  void update(@Param("user") User user);

  class UserSqlBuilder implements ProviderMethodResolver {
    public String findByEmail() {
      return new SQL()
        .SELECT("id, username, email, password, bio, image")
        .FROM("users")
        .WHERE("email = #{email}")
        .toString();
    }

    public String findByName() {
      return new SQL()
        .SELECT("id, username, email, password, bio, image")
        .FROM("users")
        .WHERE("username = #{name}")
        .toString();
    }

    public String insert() {
      return new SQL()
        .INSERT_INTO("users")
        .VALUES("id", "#{user.id}")
        .VALUES("username", "#{user.username}")
        .VALUES("email", "#{user.email}")
        .VALUES("password", "#{user.password}")
        .VALUES("bio", "#{user.bio}")
        .VALUES("image", "#{user.image}")
        .toString();
    }

    public String findById() {
      return new SQL()
        .SELECT("id, username, email, password, bio, image")
        .FROM("users")
        .WHERE("id = #{id}")
        .toString();
    }

    public String update() {
      return new SQL()
        .UPDATE("users")
        .SET("username = #{user.username}")
        .SET("email = #{user.email}")
        .SET("password = #{user.password}")
        .SET("bio = #{user.bio}")
        .SET("image = #{user.image}")
        .WHERE("id = #{user.id}")
        .toString();
    }
  }
}
