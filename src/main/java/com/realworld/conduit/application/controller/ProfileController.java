package com.realworld.conduit.application.controller;

import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.Profile;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ProfileService;
import com.realworld.conduit.domain.service.UserService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profiles/{username}")
public class ProfileController {
  private final ProfileService profileService;
  private final UserService userService;

  @GetMapping
  public ResponseEntity getProfile(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
    return profileService
      .findByUsername(username, user)
      .map(this::profileResponse)
      .orElseThrow(ResourceNotFoundException::new);
  }

  @PostMapping(path = "follow")
  public ResponseEntity follow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
    return profileService
      .findByUsername(username, user)
      .map(
        target -> {
          FollowRelation followRelation = new FollowRelation(user.getId(), target.getId());
          userService.saveRelation(followRelation);
          return profileResponse((profileService.findByUsername(username, user).get()));
        }
      ).orElseThrow(ResourceNotFoundException::new);
  }

  @DeleteMapping(path = "follow")
  public ResponseEntity unfollow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
    final var targetUser = userService.findByName(username);
    if (targetUser != null) {
      return userService
        .findRelation(user.getId(), targetUser.getId())
        .map(
          relation -> {
            userService.removeRelation(relation);
            return profileResponse(profileService.findByUsername(username, user).get());
          }
        ).orElseThrow(ResourceNotFoundException::new);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  private ResponseEntity profileResponse(Profile profile) {
    return ResponseEntity.ok(
      new HashMap<String, Object>() {
        {
          put("profile", profile);
        }
      });
  }
}
