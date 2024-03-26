package com.realworld.conduit.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface ArticleTagMapper {
  @InsertProvider(type = ArticleTagSqlBuilder.class, method = "insert")
  void insert(@Param("articleId") String articleId, @Param("tagId") String tagId);

  class ArticleTagSqlBuilder implements ProviderMethodResolver {
    public String insert() {
      return new SQL()
        .INSERT_INTO("article_tags")
        .VALUES("article_id", "#{articleId}")
        .VALUES("tag_id", "#{tagId}")
        .toString();
    }
  }
}
