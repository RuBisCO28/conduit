package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.user.UpdateUserRequest;
import com.realworld.conduit.application.resource.user.UserWithToken;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity currentUser(
    @AuthenticationPrincipal User currentUser,
    @RequestHeader(value = "X-AUTH-TOKEN") String token) {
    User user = userService.findById(currentUser.getId());
    return ResponseEntity.ok(userResponse(new UserWithToken(user, token)));
  }

  @PutMapping
  public ResponseEntity updateProfile(
    @AuthenticationPrincipal User currentUser,
    @RequestHeader(value = "X-AUTH-TOKEN") String token,
    @Valid @RequestBody UpdateUserRequest request) {
    userService.updateProfile(currentUser, request);
    User user = userService.findById(currentUser.getId());
    return ResponseEntity.ok(userResponse(new UserWithToken(user, token)));
  }

  private Map<String, Object> userResponse(UserWithToken userWithToken) {
    return new HashMap<String, Object>() {
      {
        put("user", userWithToken);
      }
    };
  }
}
