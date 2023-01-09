package com.realworld.conduit.domain.repository;

import com.realworld.conduit.domain.object.ArticleFavorite;
import java.util.Optional;

public interface ArticleFavoriteRepository {
  void save(ArticleFavorite favorite);
  Optional<ArticleFavorite> find(String articleId, String userId);
  void remove(ArticleFavorite favorite);
}
