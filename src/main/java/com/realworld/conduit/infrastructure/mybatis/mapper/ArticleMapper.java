package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper
public interface ArticleMapper {
  @InsertProvider(type = ArticleSqlBuilder.class, method = "insert")
  void insert(@Param("article") Article article);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "findBySlug")
  Article findBySlug(@Param("slug") String slug);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "findIdsByQuery")
  List<String> findIdsByQuery(@Param("tag") String tag, @Param("author") String author, @Param("favoritedBy") String favoritedBy, @Param("page") Page page);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "countByQuery")
  int countByQuery(
    @Param("tag") String tag,
    @Param("author") String author,
    @Param("favoritedBy") String favoritedBy);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "findAllByIds")
  List<Article> findAllByIds(@Param("articleIds") List<String> articleIds);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "findArticlesOfAuthors")
  List<Article> findArticlesOfAuthors(@Param("authors") List<String> authors, @Param("page") Page page);

  @SelectProvider(type = ArticleSqlBuilder.class, method = "countFeedSize")
  int countFeedSize(@Param("authors") List<String> authors);

  @UpdateProvider(type = ArticleSqlBuilder.class, method = "update")
  void update(@Param("article") Article article);

  @DeleteProvider(type = ArticleSqlBuilder.class, method = "delete")
  void delete(@Param("article") Article article);

  class ArticleSqlBuilder implements ProviderMethodResolver {
    public String insert() {
      return new SQL()
        .INSERT_INTO("articles")
        .VALUES("id", "#{article.id}")
        .VALUES("slug", "#{article.slug}")
        .VALUES("title", "#{article.title}")
        .VALUES("description", "#{article.description}")
        .VALUES("body", "#{article.body}")
        .VALUES("user_id", "#{article.userId}")
        .VALUES("created_at", "#{article.createdAt}")
        .VALUES("updated_at", "#{article.updatedAt}")
        .toString();
    }

    public String findBySlug() {
      return new SQL()
        .SELECT("id, " +
          "user_id, " +
          "title, " +
          "slug, " +
          "description, " +
          "body, " +
          "created_at, " +
          "updated_at")
        .FROM("articles")
        .WHERE("slug = #{slug}")
        .toString();
    }

    public String findIdsByQuery(final String tag, final String author, final String favoritedBy) {
      final var sql = new SQL()
        .SELECT("DISTINCT(articles.id)")
        .FROM("articles")
        .LEFT_OUTER_JOIN("article_tags ON articles.id = article_tags.article_id")
        .LEFT_OUTER_JOIN("tags ON tags.id = article_tags.tag_id")
        .LEFT_OUTER_JOIN("article_favorites ON article_favorites.article_id = articles.id")
        .LEFT_OUTER_JOIN("users AS AU ON AU.id = articles.user_id")
        .LEFT_OUTER_JOIN("users AS AFU ON AFU.id = article_favorites.user_id");
      return filterdByQuery(sql, tag, author, favoritedBy)
        .LIMIT("#{page.limit}")
        .OFFSET("#{page.offset}")
        .toString();
    }

    public String countByQuery() {
      final var sql = new SQL()
        .SELECT("count(DISTINCT(articles.id))")
        .FROM("articles")
        .LEFT_OUTER_JOIN("article_tags ON articles.id = article_tags.article_id")
        .LEFT_OUTER_JOIN("tags ON tags.id = article_tags.tag_id")
        .LEFT_OUTER_JOIN("article_favorites ON article_favorites.article_id = articles.id")
        .LEFT_OUTER_JOIN("users AS AU ON AU.id = articles.user_id")
        .LEFT_OUTER_JOIN("users AS AFU ON AFU.id = article_favorites.user_id");
      return filterdByQuery(sql, "#{tag}", "#{author}", "#{favoritedBy}").toString();
    }

    public String findAllByIds(List<String> articleIds) {
      final String inParams = getInPhraseParamString(articleIds, "articleIds");
      return new SQL()
        .SELECT("id, " +
          "user_id, " +
          "title, " +
          "slug, " +
          "description, " +
          "body, " +
          "created_at, " +
          "updated_at")
        .FROM("articles")
        .WHERE(String.format("articles.id IN (%s)", inParams))
        .ORDER_BY("articles.created_at DESC")
        .toString();
    }

    public String findArticlesOfAuthors(List<String> authors) {
      final String inParams = getInPhraseParamString(authors, "authors");
      return new SQL()
        .SELECT("articles.id, " +
          "articles.user_id, " +
          "articles.title, " +
          "articles.slug, " +
          "articles.description, " +
          "articles.body, " +
          "articles.created_at, " +
          "articles.updated_at")
        .FROM("articles")
        .LEFT_OUTER_JOIN("article_tags ON articles.id = article_tags.article_id")
        .LEFT_OUTER_JOIN("tags ON tags.id = article_tags.tag_id")
        .LEFT_OUTER_JOIN("users ON users.id = articles.user_id")
        .WHERE(String.format("users.id IN (%s)", inParams))
        .LIMIT("#{page.limit}")
        .OFFSET("#{page.offset}")
        .toString();
    }

    public String countFeedSize(List<String> authors) {
      final String inParams = getInPhraseParamString(authors, "authors");
      return new SQL()
        .SELECT("count(1)")
        .FROM("articles")
        .WHERE(String.format("user_id IN (%s)", inParams))
        .toString();
    }

    public String update() {
      return new SQL()
        .UPDATE("articles")
        .SET("title = #{article.title}")
        .SET("body = #{article.body}")
        .SET("slug = #{article.slug}")
        .SET("description = #{article.description}")
        .SET("updated_at = #{article.updatedAt}")
        .WHERE("id = #{article.id}")
        .toString();
    }

    public String delete() {
      return new SQL()
        .DELETE_FROM("articles")
        .WHERE("id = #{article.id}")
        .toString();
    }

    private String getInPhraseParamString(List<String> values, String paramName) {
      final StringBuilder paramString = new StringBuilder();
      int i = 0;
      for (String value : values) {
        if (i != 0) {
          paramString.append(",");
        }
        paramString.append(String.format("#{%s[%s]}", paramName, i));
        i++;
      }
      return paramString.toString();
    }

    private static SQL filterdByQuery(SQL sql, String tag, String author, String favoritedBy) {
      if (!StringUtils.isEmpty(tag)) {
        sql.WHERE("tags.name = #{tag}");
      }
      if (!StringUtils.isEmpty(author)) {
        sql.WHERE("AU.username = #{author}");
      }
      if (!StringUtils.isEmpty(favoritedBy)) {
        sql.WHERE("AFU.username = #{favoritedBy}");
      }
      return sql;
    }
  }
}
