package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.comment.MultipleCommentsResponse;
import com.realworld.conduit.application.resource.comment.NewCommentRequest;
import com.realworld.conduit.application.resource.comment.SingleCommentResponse;
import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.Comment;
import com.realworld.conduit.domain.object.Profile;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ArticleService;
import com.realworld.conduit.domain.service.CommentService;
import com.realworld.conduit.domain.service.ProfileService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/articles/{slug}/comments")
public class CommentsController {
  private final ArticleService articleService;
  private final CommentService commentService;
  private final ProfileService profileService;

  @PostMapping
  public ResponseEntity createComment(
    @PathVariable("slug") String slug,
    @RequestBody @Validated NewCommentRequest request,
    @AuthenticationPrincipal User user,
    @Nonnull BindingResult result
  ) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    Article article =
      articleService.findBySlug(slug, user).orElseThrow(ResourceNotFoundException::new);
    Comment comment = commentService.create(request, user, article);
    final var profile = profileService.findByUserId(article.getUserId(), user);
    Profile authorProfile = profileService
      .findByUsername(profile.getUsername(), user)
      .orElseThrow(ResourceNotFoundException::new);
    return ResponseEntity.ok(SingleCommentResponse.from(comment, authorProfile));
  }

  @GetMapping
  public ResponseEntity getComments(
    @PathVariable("slug") String slug,
    @AuthenticationPrincipal User user
  ) {
    Article article =
      articleService.findBySlug(slug, user).orElseThrow(ResourceNotFoundException::new);
    final var profile = profileService.findByUserId(article.getUserId(), user);
    Profile authorProfile = profileService
      .findByUsername(profile.getUsername(), user)
      .orElseThrow(ResourceNotFoundException::new);
    List<Comment> comments = commentService.findByArticleId(article.getId());
    List<SingleCommentResponse> responses =
      comments.stream().map(
        comment -> SingleCommentResponse.from(comment, authorProfile)
      ).collect(Collectors.toList());
    return ResponseEntity.ok(MultipleCommentsResponse.from(responses));
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteComment(
    @PathVariable("slug") String slug,
    @PathVariable("id") String commentId,
    @AuthenticationPrincipal User user
  ) {
    Article article =
      articleService.findBySlug(slug, user).orElseThrow(ResourceNotFoundException::new);
    return commentService
      .findById(article.getId(), commentId)
      .map(comment -> {
        commentService.remove(comment);
          return ResponseEntity.noContent().build();
      })
      .orElseThrow(ResourceNotFoundException::new);
  }
}
