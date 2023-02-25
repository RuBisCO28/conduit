package com.realworld.conduit.domain.service;

import com.realworld.conduit.domain.object.ArticleFavorite;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleFavoriteMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleFavoriteService {
  private final ArticleFavoriteMapper articleFavoriteMapper;

  public void create(ArticleWithSummary article, User user) {
    ArticleFavorite favorite = new ArticleFavorite(article.getId(), user.getId());
    articleFavoriteMapper.insert(favorite);
  }

  public Optional<ArticleFavorite> find(String articleId, String userId) {
    return articleFavoriteMapper.find(articleId, userId);
  }

  public void remove(ArticleFavorite favorite) {
    articleFavoriteMapper.delete(favorite);
  }

  public int countByAuthorId(String articleId) {
    return articleFavoriteMapper.countByAuthorId(articleId);
  }
}
