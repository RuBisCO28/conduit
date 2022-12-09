package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  User findById(@Param("id") String id);
  void insert(@Param("user") User user);
  User findByEmail(@Param("email") String email);
  User findByUsername(@Param("username") String username);
  void update(@Param("user") User user);
}
