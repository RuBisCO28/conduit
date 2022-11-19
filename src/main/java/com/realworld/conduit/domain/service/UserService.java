package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.user.NewUserRequest;
import com.realworld.conduit.application.resource.user.UpdateUserRequest;
import com.realworld.conduit.domain.exception.InvalidAuthenticationException;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.UserRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final String defaultImage = "https://static.productionready.io/images/smiley-cyrus.jpg";

  public User createUser(@Valid NewUserRequest request) {
    User user =
      new User(
        request.getEmail(),
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        "",
        defaultImage);
    userRepository.save(user);
    return user;
  }

  public User findByEmail(String email) {
    final var user = userRepository.findByEmail(email);
    if (user == null) {
      throw new InvalidAuthenticationException();
    }
    return user;
  }

  public User findById(String id) {
    final var user = userRepository.findById(id);
    if (user == null) {
      throw new InvalidAuthenticationException();
    }
    return user;
  }

  public void updateProfile(User user, UpdateUserRequest request) {
    user.update(
      request.getEmail(),
      request.getUsername(),
      request.getPassword(),
      request.getBio(),
      request.getImage());
    userRepository.update(user);
  }
}
