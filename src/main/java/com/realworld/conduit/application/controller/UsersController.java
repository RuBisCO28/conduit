package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.user.LoginUserRequest;
import com.realworld.conduit.application.resource.user.NewUserRequest;
import com.realworld.conduit.application.resource.user.UserResponse;
import com.realworld.conduit.domain.exception.InvalidAuthenticationException;
import com.realworld.conduit.domain.service.JwtService;
import com.realworld.conduit.domain.service.UserService;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UsersController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @PostMapping("login")
  public ResponseEntity userLogin(@Validated @RequestBody LoginUserRequest request, @Nonnull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    final var user = userService.findByEmail(request.getEmail());
    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.ok(UserResponse.from(user, jwtService.toToken(user)));
    } else {
      throw new InvalidAuthenticationException();
    }
  }

  @PostMapping
  public ResponseEntity createUser(@Validated @RequestBody NewUserRequest request, @Nonnull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    final var user = userService.createUser(request);
    return ResponseEntity.status(201).body(UserResponse.from(user, jwtService.toToken(user)));
  }
}
