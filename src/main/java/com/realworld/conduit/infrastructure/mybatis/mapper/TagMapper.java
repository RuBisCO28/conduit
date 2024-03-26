package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Tag;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface TagMapper {

  @SelectProvider(TagSqlBuilder.class)
  Tag findByName(@Param("name") String name);

  @InsertProvider(TagSqlBuilder.class)
  void insert(@Param("tag") Tag tag);

  @SelectProvider(TagSqlBuilder.class)
  List<String> findAll();

  @SelectProvider(TagSqlBuilder.class)
  List<Tag> findByArticleId(@Param("articleId") String articleId);

  class TagSqlBuilder implements ProviderMethodResolver {
    public String findByName() {
      return new SQL() {{
        SELECT("id, name");
        FROM("tags");
        WHERE("name = #{name}");
      }}.toString();
    }

    public String insert() {
      return new SQL() {{
        INSERT_INTO("tags");
        VALUES("id", "#{tag.id}");
        VALUES("name", "#{tag.name}");
      }}.toString();
    }

    public String findAll() {
      return new SQL() {{
        SELECT("name");
        FROM("tags");
      }}.toString();
    }

    public String findByArticleId() {
      return new SQL()
        .SELECT("tags.id, tags.name")
        .FROM("tags")
        .INNER_JOIN("article_tags ON tags.id = article_tags.tag_id")
        .WHERE("article_tags.article_id = #{articleId}")
        .toString();
    }
  }
}
