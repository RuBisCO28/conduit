package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Comment;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface CommentMapper {
  @InsertProvider(type = CommentSqlBuilder.class, method = "insert")
  void insert(@Param("comment") Comment comment);

  @SelectProvider(type = CommentSqlBuilder.class, method = "findByArticleId")
  List<Comment> findByArticleId(@Param("articleId") String articleId);

  @SelectProvider(type = CommentSqlBuilder.class, method = "findById")
  Optional<Comment> findById(@Param("articleId") String articleId, @Param("commentId") String commentId);

  @DeleteProvider(type = CommentSqlBuilder.class, method = "delete")
  void delete(@Param("comment") Comment comment);

  class CommentSqlBuilder implements ProviderMethodResolver {
    public String insert() {
      return new SQL()
        .INSERT_INTO("comments")
        .VALUES("id", "comment.id")
        .VALUES("body", "comment.body")
        .VALUES("user_id", "comment.userId")
        .VALUES("article_id", "comment.articleId")
        .VALUES("created_at", "comment.createdAt")
        .VALUES("updated_at", "comment.createdAt")
        .toString();
    }

    public String findByArticleId() {
      return new SQL()
        .SELECT("id, body, user_id, article_id, created_at")
        .FROM("comments")
        .WHERE("article_id = #{articleId}")
        .toString();
    }

    public String findById() {
      return new SQL()
        .SELECT("id, body, user_id, article_id, created_at")
        .FROM("comments")
        .WHERE("id = #{commentId} and article_id = #{articleId}")
        .toString();
    }

    public String delete() {
      return new SQL()
        .DELETE_FROM("comments")
        .WHERE("id = #{comment.id}")
        .toString();
    }
  }
}
