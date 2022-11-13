package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.user.LoginUserRequest;
import com.realworld.conduit.application.resource.user.NewUserRequest;
import com.realworld.conduit.application.resource.user.UserWithToken;
import com.realworld.conduit.domain.exception.InvalidAuthenticationException;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.JwtService;
import com.realworld.conduit.domain.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.NonNull;
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
  @PostMapping
  public ResponseEntity createUser(@RequestBody @Validated NewUserRequest request, @NonNull BindingResult result) {
    User user = userService.createUser(request);
    return ResponseEntity.status(201)
      .body(userResponse(new UserWithToken(user, jwtService.toToken(user))));
  }

  @PostMapping("login")
  public ResponseEntity userLogin(@Valid @RequestBody LoginUserRequest request, @NonNull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    User user = userService.findByEmail(request.getEmail());
    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.ok(
        userResponse(new UserWithToken(user, jwtService.toToken(user))));
    } else {
      throw new InvalidAuthenticationException();
    }
  }

  private Map<String, Object> userResponse(UserWithToken userWithToken) {
    return new HashMap<String, Object>() {
      {
        put("user", userWithToken);
      }
    };
  }
}
