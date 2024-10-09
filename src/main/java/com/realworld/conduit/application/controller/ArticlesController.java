package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.article.MultipleArticlesResponse;
import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.application.resource.article.SingleArticleResponse;
import com.realworld.conduit.application.resource.article.UpdateArticleRequest;
import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.Page;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ArticleFavoriteService;
import com.realworld.conduit.domain.service.ArticleService;
import com.realworld.conduit.domain.service.ProfileService;
import com.realworld.conduit.domain.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/articles")
public class ArticlesController {

  private final ArticleService articleService;
  private final TagService tagService;
  private final ArticleFavoriteService articleFavoriteService;
  private final ProfileService profileService;

  @PostMapping
  public ResponseEntity createArticle(
    @RequestBody @Validated NewArticleRequest request,
    @AuthenticationPrincipal User user,
    @Nonnull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    Article article = articleService.create(request, user);
    final var tags = tagService.findByArticleId(article.getId());
    final var profile = profileService.findByUserId(article.getUserId(), user);
    final boolean isFavorited = articleFavoriteService.
      find(article.getId(), user.getId()).isPresent();
    final int favoriteCount = articleFavoriteService.countByAuthorId(article.getId());
    return ResponseEntity.ok(SingleArticleResponse.from(article, tags, isFavorited, favoriteCount, profile));
  }

  @GetMapping
  public ResponseEntity getPagedArticles(
    @Validated PagedArticlesRequest request,
    @AuthenticationPrincipal User user
  ) {
    final var recentArticles = articleService.findRecentArticles(request);
    final var articleResponses = articleResponses(recentArticles.getArticles(), user);
    return ResponseEntity.ok(MultipleArticlesResponse.from(articleResponses, recentArticles.getCount()));
  }

  @GetMapping(path = "feed")
  public ResponseEntity getFeed(
    @RequestParam(value = "offset", defaultValue = "0") int offset,
    @RequestParam(value = "limit", defaultValue = "20") int limit,
    @AuthenticationPrincipal User user) {
    final var feedArticles = articleService.findUserFeed(user, new Page(offset, limit));
    final var articleResponses = articleResponses(feedArticles.getArticles(), user);
    return ResponseEntity.ok(MultipleArticlesResponse.from(articleResponses, feedArticles.getCount()));
  }

  @GetMapping("{slug}")
  public ResponseEntity getArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    final var article = articleService
      .findBySlug(slug, user)
      .orElseThrow(ResourceNotFoundException::new);
    final var tags = tagService.findByArticleId(article.getId());
    final var isFavorited = articleFavoriteService.find(article.getId(), user.getId()).isPresent();
    final var favoriteCount = articleFavoriteService.countByAuthorId(article.getId());
    final var profile = profileService.findByUserId(article.getUserId(), user);
    return ResponseEntity.ok(SingleArticleResponse.from(article, tags, isFavorited, favoriteCount, profile));
  }

  @PutMapping("{slug}")
  public ResponseEntity updateArticle(
    @PathVariable("slug") String slug,
    @AuthenticationPrincipal User user,
    @RequestBody @Validated UpdateArticleRequest request,
    @Nonnull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    return articleService
      .findBySlug(slug, user)
      .map(
        article -> {
          Article updatedArticle = articleService.update(article, request);
          final var tags = tagService.findByArticleId(updatedArticle.getId());
          final var isFavorited = articleFavoriteService.find(updatedArticle.getId(), user.getId()).isPresent();
          final var favoriteCount = articleFavoriteService.countByAuthorId(updatedArticle.getId());
          final var profile = profileService.findByUserId(updatedArticle.getUserId(), user);
          return ResponseEntity.ok(SingleArticleResponse.from(updatedArticle, tags, isFavorited, favoriteCount, profile));
        }
      )
      .orElseThrow(ResourceNotFoundException::new);
  }

  @DeleteMapping("{slug}")
  public ResponseEntity deleteArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    return articleService
      .findBySlug(slug, user)
      .map(
        article -> {
          articleService.delete(article);
          return ResponseEntity.noContent().build();
        }
      )
      .orElseThrow(ResourceNotFoundException::new);
  }

  private List<SingleArticleResponse> articleResponses(List<Article> articles, @Nonnull User user) {
    return articles.stream().map(
      article -> {
        final var tags = tagService.findByArticleId(article.getId());
        final var profile = profileService.findByUserId(article.getUserId(), user);
        final boolean isFavorited = articleFavoriteService.
          find(article.getId(), user.getId()).isPresent();
        final int favoriteCount = articleFavoriteService.countByAuthorId(article.getId());
        return SingleArticleResponse.from(article, tags, isFavorited, favoriteCount, profile);
      }
    ).collect(Collectors.toList());
  }
}
