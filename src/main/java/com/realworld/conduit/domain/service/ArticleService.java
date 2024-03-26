package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.application.resource.article.UpdateArticleRequest;
import com.realworld.conduit.domain.object.*;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleTagMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.FollowRelationMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.TagMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleMapper articleMapper;
  private final TagMapper tagMapper;
  private final ArticleTagMapper articleTagMapper;
  private final FollowRelationMapper followRelationMapper;

  public Article create(@Valid NewArticleRequest request, User creator) {
    Article article =
      new Article(
        request.getTitle(),
        request.getDescription(),
        request.getBody(),
        creator.getId());
    final var tags = request.getTagList().stream().map(Tag::new).collect(Collectors.toList());
    for (Tag tag : tags) {
      Tag targetTag = tagMapper.findByName(tag.getName());
      if (targetTag == null) {
        tagMapper.insert(tag);
        final Tag newTag = tagMapper.findByName(tag.getName());
        articleTagMapper.insert(article.getId(), newTag.getId());
      } else {
        articleTagMapper.insert(article.getId(), targetTag.getId());
      }
    }
    articleMapper.insert(article);
    return article;
  }

  public Optional<Article> findBySlug(String slug, User user) {
    final var article = articleMapper.findBySlug(slug);
    if (article == null) {
      return Optional.empty();
    } else {
      return Optional.of(article);
    }
  }

  public ArticlesWithCount findRecentArticles(@NonNull PagedArticlesRequest request) {
    final var page = new Page(Integer.parseInt(request.getOffset()), Integer.parseInt(request.getLimit()));
    final List<String> articleIds = articleMapper.findIdsByQuery(request.getTag(), request.getAuthor(), request.getFavoritedBy(), page);
    final int articleCount = articleMapper.countByQuery(request.getTag(), request.getAuthor(), request.getFavoritedBy());
    if (articleIds.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), articleCount);
    } else {
      final List<Article> articles = articleMapper.findAllByIds(articleIds);
      return new ArticlesWithCount(articles, articleCount);
    }
  }

  public ArticlesWithCount findUserFeed(User user, Page page) {
    List<String> followdUsers = followRelationMapper.followedUsers(user.getId()).stream().map(
      FollowRelation::getTargetId).collect(Collectors.toList());
    if (followdUsers.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), 0);
    } else {
      List<Article> articles = articleMapper.findArticlesOfAuthors(followdUsers, page);
      final int count = articleMapper.countFeedSize(followdUsers);
      return new ArticlesWithCount(articles, count);
    }
  }

  public Article update(Article article, @Valid UpdateArticleRequest request) {
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
    articleMapper.update(article);
    return article;
  }

  public void delete(Article article) {
    articleMapper.delete(article);
  }
}

