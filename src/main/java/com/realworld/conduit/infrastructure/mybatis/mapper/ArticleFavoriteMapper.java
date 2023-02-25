package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.ArticleFavorite;
import java.util.Optional;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface ArticleFavoriteMapper {
  @InsertProvider(type = ArticleFavoriteSqlBuilder.class, method = "insert")
  void insert(@Param("favorite") ArticleFavorite favorite);

  @SelectProvider(type = ArticleFavoriteSqlBuilder.class, method = "find")
  Optional<ArticleFavorite> find(@Param("articleId") String ArticleId, @Param("userId") String userId);

  @DeleteProvider(type = ArticleFavoriteSqlBuilder.class, method = "delete")
  void delete(@Param("favorite") ArticleFavorite favorite);

  @SelectProvider(type = ArticleFavoriteSqlBuilder.class, method = "countByAuthorId")
  int countByAuthorId(@Param("articleId") String articleId);

  class ArticleFavoriteSqlBuilder implements ProviderMethodResolver {
    public String insert() {
      return new SQL()
        .INSERT_INTO("article_favorites")
        .VALUES("article_id", "#{favorite.articleId}")
        .VALUES("user_id", "#{favorite.userId}")
        .toString();
    }

    public String find() {
      return new SQL()
        .SELECT("article_id as articleFavoriteArticleId, user_id as articleFavoriteUserId")
        .FROM("article_favorites")
        .WHERE("article_id = #{articleId} and user_id = #{userId}")
        .toString();
    }

    public String delete() {
      return new SQL()
        .DELETE_FROM("article_favorites")
        .WHERE("article_id = #{favorite.articleId} and user_id = #{favorite.userId}")
        .toString();
    }

    public String countByAuthorId() {
      return new SQL()
        .SELECT("count(user_id)")
        .FROM("article_favorites")
        .WHERE("article_id = #{articleId}")
        .toString();
    }
  }
}
