package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.object.ArticleFavorite;
import com.realworld.conduit.domain.repository.ArticleFavoriteRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleFavoriteMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisArticleFavoriteRepository implements ArticleFavoriteRepository {
  private final ArticleFavoriteMapper favoriteMapper;

  @Override
  @Transactional
  public void save(ArticleFavorite favorite) {
    favoriteMapper.insert(favorite);
  }

  @Override
  @Transactional
  public Optional<ArticleFavorite> find(String articleId, String userId) {
    return favoriteMapper.find(articleId, userId);
  }

  @Override
  @Transactional
  public void remove(ArticleFavorite favorite) {
    favoriteMapper.delete(favorite);
  }

  @Override
  @Transactional
  public int countByAuthorId(String articleId) {
    return favoriteMapper.countByAuthorId(articleId);
  }
}
