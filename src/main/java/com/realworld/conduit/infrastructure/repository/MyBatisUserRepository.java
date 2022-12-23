package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.exception.DuplicatedUserCreationException;
import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.UserRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.FollowMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {
  private final UserMapper userMapper;
  private final FollowMapper followMapper;

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
  public User findByName(String name) {
    return userMapper.findByName(name);
  }

  @Override
  @Transactional
  public User findById(String id) {
    return userMapper.findById(id);
  }

  @Override
  @Transactional
  public void update(User user) {
    userMapper.update(user);
  }

  @Override
  @Transactional
  public FollowRelation findRelation(String userId, String targetId) {
    return userMapper.findRelation(userId, targetId);
  }

  @Override
  @Transactional
  public void saveRelation(FollowRelation relation) {
    userMapper.saveRelation(relation);
  }

  @Override
  @Transactional
  public boolean isFollowing(String userId, String targetUserId) {
    return followMapper.isUserFollowing(userId, targetUserId);
  }

  @Override
  @Transactional
  public void deleteRelation(FollowRelation relation) {
    userMapper.deleteRelation(relation);
  }
}
