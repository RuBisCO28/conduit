package com.realworld.conduit.domain.repository;

import com.realworld.conduit.domain.object.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  void save(Comment comment);
  List<Comment> findByArticleId(String articleId);
  Optional<Comment> findById(String articleId, String commentId);
  void remove(Comment comment);
}
