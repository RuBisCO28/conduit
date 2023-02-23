package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.application.resource.article.UpdateArticleRequest;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.ArticlesWithCount;
import com.realworld.conduit.domain.object.FollowRelation;
import com.realworld.conduit.domain.object.Page;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.ArticleRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.FollowRelationMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final FollowRelationMapper followRelationMapper;

  public Article create(@Valid NewArticleRequest request, User creator) {
    Article article =
      new Article(
        request.getTitle(),
        request.getDescription(),
        request.getBody(),
        request.getTagList(),
        creator.getId());
    articleRepository.save(article);
    return article;
  }

  public Optional<ArticleWithSummary> findBySlug(String slug, User user) {
    ArticleWithSummary article = articleRepository.findBySlug(slug);
    if (article == null) {
      return Optional.empty();
    } else {
      return Optional.of(article);
    }
  }

  public ArticlesWithCount findRecentArticles(@NonNull PagedArticlesRequest request) {
    final var page = new Page(Integer.parseInt(request.getOffset()), Integer.parseInt(request.getLimit()));
    final List<String> articleIds = articleRepository.fetchIdsByQuery(request.getTag(), request.getAuthor(), request.getFavoritedBy(), page);
    final int articleCount = articleRepository.countByQuery(request.getTag(), request.getAuthor(), request.getFavoritedBy());
    if (articleIds.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), articleCount);
    } else {
      final List<ArticleWithSummary> articles = articleRepository.findAllByIds(articleIds);
      return new ArticlesWithCount(articles, articleCount);
    }
  }

  public ArticlesWithCount findUserFeed(User user, Page page) {
    List<String> followdUsers = followRelationMapper.followedUsers(user.getId()).stream().map(
      FollowRelation::getTargetId).collect(Collectors.toList());
    if (followdUsers.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), 0);
    } else {
      List<ArticleWithSummary> articles = articleRepository.findArticlesOfAuthors(followdUsers, page);
      final int count = articleRepository.countFeedSize(followdUsers);
      return new ArticlesWithCount(articles, count);
    }
  }

  public ArticleWithSummary update(ArticleWithSummary article, @Valid UpdateArticleRequest request) {
    final var title = request.getTitle();
    final var description = request.getDescription();
    final var body = request.getBody();
    if (title == null && description == null && body == null) {
      return article;
    }
    if (title != null) {
      article.setTitle(title);
      article.setSlug(Article.toSlug(title));
      article.setUpdatedAt(LocalDateTime.now());
    }
    if (description != null) {
      article.setDescription(description);
      article.setUpdatedAt(LocalDateTime.now());
    }
    if (body != null) {
      article.setBody(body);
      article.setUpdatedAt(LocalDateTime.now());
    }
    articleRepository.update(article);
    return article;
  }

  public void delete(ArticleWithSummary article) {
    articleRepository.delete(article);
  }
}

