package com.realworld.conduit.domain.service;

import com.realworld.conduit.application.resource.comment.NewCommentRequest;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Comment;
import com.realworld.conduit.domain.object.User;
import com.realworld.conduit.domain.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  public Comment create(@Valid NewCommentRequest request, User creator, ArticleWithSummary article) {
    Comment comment =
      new Comment(
        request.getBody(),
        creator.getId(),
        article.getId()
      );
    commentRepository.save(comment);
    return comment;
  }

  public List<Comment> findByArticleId(String articleId) {
    return commentRepository.findByArticleId(articleId);
  }

  public Optional<Comment> findById(String articleId, String commentId) {
    return commentRepository.findById(articleId, commentId);
  }

  public void remove(Comment comment) {
    commentRepository.remove(comment);
  }
}
