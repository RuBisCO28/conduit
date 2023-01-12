package com.realworld.conduit.domain.service;

import com.realworld.conduit.domain.object.ArticleFavorite;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.ArticleFavoriteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleFavoriteService {
  private final ArticleFavoriteRepository favoriteRepository;

  public void create(ArticleWithSummary article, User user) {
    ArticleFavorite favorite = new ArticleFavorite(article.getId(), user.getId());
    favoriteRepository.save(favorite);
  }

  public Optional<ArticleFavorite> find(String articleId, String userId) {
    return favoriteRepository.find(articleId, userId);
  }

  public void remove(ArticleFavorite favorite) {
    favoriteRepository.remove(favorite);
  }

  public int countByAuthorId(String articleId) {
    return favoriteRepository.countByAuthorId(articleId);
  }
}
