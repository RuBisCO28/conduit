package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.user.NewUserRequest;
import com.realworld.conduit.application.resource.user.UpdateUserRequest;
import com.realworld.conduit.domain.exception.DuplicatedUserCreationException;
import com.realworld.conduit.domain.exception.InvalidAuthenticationException;
import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.UserRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final String DEFAULT_IMAGE = "https://static.productionready.io/images/smiley-cyrus.jpg";

  public User findByEmail(String email) {
    final var user = userMapper.findByEmail(email);
    if (user == null) {
      throw new InvalidAuthenticationException();
    }
    return user;
  }

  public User findByName(String name) {
    final var user = userMapper.findByName(name);
    if (user == null) {
      throw new InvalidAuthenticationException();
    }
    return user;
  }

  public User findById(String id) {
    final var user = userMapper.findById(id);
    if (user == null) {
      throw new InvalidAuthenticationException();
    }
    return user;
  }

  @Transactional
  public User createUser(NewUserRequest request) {
    final var user =
      new User(
        request.getEmail(),
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        "",
        DEFAULT_IMAGE);
    userMapper.insert(user);
    return user;
  }

  @Transactional
  public void updateProfile(User user, UpdateUserRequest request) {
    user.update(
      request.getEmail(),
      request.getUsername(),
      request.getPassword(),
      request.getBio(),
      request.getImage());
    userMapper.update(user);
  }

  public void saveRelation(FollowRelation followRelation) {
    if (!findRelation(followRelation.getUserId(), followRelation.getTargetId()).isPresent()) {
      userRepository.saveRelation(followRelation);
    }
  }

  public Optional<FollowRelation> findRelation(String userId, String targetUserId) {
    return Optional.ofNullable(userRepository.findRelation(userId, targetUserId));
  }

  public void removeRelation(FollowRelation followRelation) {
    userRepository.deleteRelation(followRelation);
  }
}
