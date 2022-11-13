package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.exception.DuplicatedUserCreationException;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.UserRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {
  private final UserMapper userMapper;

  @Override
  @Transactional
  public void save(User user) {
    if (userMapper.findById(user.getId()) == null) {
      userMapper.insert(user);
    } else {
      throw new DuplicatedUserCreationException();
    }
  }

  @Override
  @Transactional
  public User findByEmail(String email) {
    return userMapper.findByEmail(email);
  }

  @Override
  @Transactional
  public User findByUsername(String username) {
    return userMapper.findByUsername(username);
  }
}
