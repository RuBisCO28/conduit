package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.article.NewArticleRequest;
import com.realworld.conduit.application.resource.article.PagedArticlesRequest;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.ArticlesWithCount;
import com.realworld.conduit.domain.object.Page;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.ArticleRepository;
import com.realworld.conduit.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

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

  public ArticlesWithCount findRecentArticles(@NonNull PagedArticlesRequest request) {
    final var page = new Page(Integer.parseInt(request.getOffset()), Integer.parseInt(request.getLimit()));
    final List<String> articleIds = articleRepository.fetchIdsByQuery(request.getTag(), request.getAuthor(), page);
    final int articleCount = articleRepository.countByTagAndAuthor(request.getTag(), request.getAuthor());
    if (articleIds.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), articleCount);
    } else {
      final List<ArticleWithSummary> articles = articleRepository.findAllByIds(articleIds);
      return new ArticlesWithCount(articles, articleCount);
    }
  }

  public ArticlesWithCount findUserFeed(User user, Page page) {
    List<String> followdUsers = userRepository.followedUsers(user.getId());
    if (followdUsers.size() == 0) {
      return new ArticlesWithCount(new ArrayList<>(), 0);
    } else {
      List<ArticleWithSummary> articles = articleRepository.findArticlesOfAuthors(followdUsers, page);
      final int count = articleRepository.countFeedSize(followdUsers);
      return new ArticlesWithCount(articles, count);
    }
  }
}

