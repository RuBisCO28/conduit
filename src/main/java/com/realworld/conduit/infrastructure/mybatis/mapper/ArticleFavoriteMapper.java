package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.ArticleFavorite;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleFavoriteMapper {
  void insert(@Param("favorite") ArticleFavorite favorite);
  Optional<ArticleFavorite> find(@Param("articleId") String ArticleId, @Param("userId") String userId);
  void delete(@Param("favorite") ArticleFavorite favorite);
}
