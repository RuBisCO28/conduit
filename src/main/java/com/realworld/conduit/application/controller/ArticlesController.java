package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.service.ArticleService;
import java.util.HashMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/articles")
public class ArticlesController {

  private final ArticleService articleService;

  @PostMapping
  public ResponseEntity createArticle(@RequestBody @Validated NewArticleRequest request, @NonNull BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(result.getFieldErrors());
    }
    Article article = articleService.create(request);
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
}
