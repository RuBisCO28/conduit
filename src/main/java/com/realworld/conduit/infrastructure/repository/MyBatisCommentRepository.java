package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.object.Comment;
import com.realworld.conduit.domain.repository.CommentRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.CommentMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisCommentRepository implements CommentRepository {
  private final CommentMapper commentMapper;
  @Override
  @Transactional
  public void save(Comment comment) {
    commentMapper.insert(comment);
  }

  @Override
  @Transactional
  public List<Comment> findByArticleId(String articleId) {
    return commentMapper.findByArticleId(articleId);
  }

  @Override
  @Transactional
  public Optional<Comment> findById(String articleId, String commentId) {
    return commentMapper.findById(articleId, commentId);
  }

  @Override
  @Transactional
  public void remove(Comment comment) {
    commentMapper.delete(comment);
  }
}
