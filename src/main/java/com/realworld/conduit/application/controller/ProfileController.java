package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.profile.ProfileResponse;
import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ProfileService;
import com.realworld.conduit.domain.service.UserService;
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
    return ResponseEntity.ok(
      profileService
        .findByUsername(username, user)
        .map(ProfileResponse::from)
        .orElseThrow(ResourceNotFoundException::new)
    );
  }

  @PostMapping(path = "follow")
  public ResponseEntity follow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
    return profileService
      .findByUsername(username, user)
      .map(
        target -> {
          if (userService.findRelation(user.getId(), target.getId()) == null) {
            FollowRelation followRelation = new FollowRelation(user.getId(), target.getId());
            userService.saveRelation(followRelation);
          }
          return ResponseEntity.ok(ProfileResponse.from((profileService.findByUsername(username, user).get())));
        }
      ).orElseThrow(ResourceNotFoundException::new);
  }

  @DeleteMapping(path = "follow")
  public ResponseEntity unfollow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
    final var targetUser = userService.findByName(username);
    if (targetUser != null) {
      final var relation = userService.findRelation(user.getId(), targetUser.getId());
      if (relation != null) {
        userService.removeRelation(relation);
      }
      return ResponseEntity.ok(ProfileResponse.from(profileService.findByUsername(username, user).get()));
    } else {
      throw new ResourceNotFoundException();
    }
  }
}
