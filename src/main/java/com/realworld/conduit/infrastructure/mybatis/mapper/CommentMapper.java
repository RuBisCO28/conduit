package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Comment;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
  void insert(@Param("comment") Comment comment);
  List<Comment> findByArticleId(@Param("articleId") String articleId);
  Optional<Comment> findById(@Param("articleId") String articleId, @Param("commentId") String commentId);
  void delete(@Param("comment") Comment comment);
}
