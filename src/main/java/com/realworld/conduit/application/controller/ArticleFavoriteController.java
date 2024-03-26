package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.article.SingleArticleResponse;
import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ArticleFavoriteService;
import com.realworld.conduit.domain.service.ArticleService;
import com.realworld.conduit.domain.service.ProfileService;
import com.realworld.conduit.domain.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/articles/{slug}/favorite")
public class ArticleFavoriteController {
  private final ArticleService articleService;
  private final TagService tagService;
  private final ArticleFavoriteService articleFavoriteService;
  private final ProfileService profileService;

  @PostMapping
  public ResponseEntity favoriteArticle(
    @PathVariable("slug") String slug,
    @AuthenticationPrincipal User user
  ) {
    Article article = articleService.findBySlug(slug, user).orElseThrow(
      ResourceNotFoundException::new);
    articleFavoriteService.create(article, user);
    final var tags = tagService.findByArticleId(article.getId());
    final var profile = profileService.findByUserId(article.getUserId(), user);
    final boolean isFavorited = articleFavoriteService.
      find(article.getId(), user.getId()).isPresent();
    final int favoriteCount = articleFavoriteService.countByAuthorId(article.getId());
    return ResponseEntity.ok(SingleArticleResponse.from(article, tags, isFavorited, favoriteCount, profile));
  }

  @DeleteMapping
  public ResponseEntity unfavoriteArticle(
    @PathVariable("slug") String slug,
    @AuthenticationPrincipal User user
  ) {
    Article article = articleService.findBySlug(slug, user).orElseThrow(
      ResourceNotFoundException::new);
    articleFavoriteService
      .find(article.getId(), user.getId())
      .ifPresent(
        articleFavoriteService::remove
      );
    final var tags = tagService.findByArticleId(article.getId());
    final var profile = profileService.findByUserId(article.getUserId(), user);
    final boolean isFavorited = articleFavoriteService.
      find(article.getId(), user.getId()).isPresent();
    final int favoriteCount = articleFavoriteService.countByAuthorId(article.getId());
    return ResponseEntity.ok(SingleArticleResponse.from(article, tags, isFavorited, favoriteCount, profile));
  }
}
