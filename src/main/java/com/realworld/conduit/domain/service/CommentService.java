package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.comment.NewCommentRequest;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.Comment;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.infrastructure.mybatis.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentMapper commentMapper;

  public Comment create(@Valid NewCommentRequest request, User creator, Article article) {
    Comment comment =
      new Comment(
        request.getBody(),
        creator.getId(),
        article.getId()
      );
    commentMapper.insert(comment);
    return comment;
  }

  public List<Comment> findByArticleId(String articleId) {
    return commentMapper.findByArticleId(articleId);
  }

  public Optional<Comment> findById(String articleId, String commentId) {
    return commentMapper.findById(articleId, commentId);
  }

  public void remove(Comment comment) {
    commentMapper.delete(comment);
  }
}
