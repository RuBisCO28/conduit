package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.application.resource.article.UpdateArticleRequest;
import com.realworld.conduit.domain.exception.ResourceNotFoundException;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Page;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.service.ArticleService;
import java.util.HashMap;
import lombok.NonNull;
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

  @PostMapping
  public ResponseEntity createArticle(
    @RequestBody @Validated NewArticleRequest request,
    @AuthenticationPrincipal User user,
    @NonNull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    Article article = articleService.create(request, user);
    return ResponseEntity.ok(
      new HashMap<String, Object>() {
        {
          put("article", article);
        }
      });
  }

  @GetMapping
  public ResponseEntity getPagedArticles(@Validated PagedArticlesRequest request) {
    return ResponseEntity.ok(
      articleService.findRecentArticles(request)
    );
  }

  @GetMapping(path = "feed")
  public ResponseEntity getFeed(
    @RequestParam(value = "offset", defaultValue = "0") int offset,
    @RequestParam(value = "limit", defaultValue = "20") int limit,
    @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(articleService.findUserFeed(user, new Page(offset, limit)));
  }

  @GetMapping("{slug}")
  public ResponseEntity getArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    return articleService
      .findBySlug(slug, user)
      .map(this::articleResponse)
      .orElseThrow(ResourceNotFoundException::new);
  }

  @PutMapping("{slug}")
  public ResponseEntity updateArticle(
    @PathVariable("slug") String slug,
    @AuthenticationPrincipal User user,
    @RequestBody @Validated UpdateArticleRequest request,
    @NonNull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    return articleService
      .findBySlug(slug, user)
      .map(
        article -> {
          ArticleWithSummary updatedArticle = articleService.update(article, request);
          return ResponseEntity.ok(
            articleResponse(updatedArticle)
          );
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

  private ResponseEntity articleResponse(ArticleWithSummary article) {
    return ResponseEntity.ok(
      new HashMap<String, Object>() {
        {
          put("article", article);
        }
      });
  }
}
