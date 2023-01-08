package com.realworld.conduit.domain.object;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class Comment {
  private String id;
  private String body;
  private String userId;
  private String articleId;
  private LocalDateTime createdAt;

  public Comment(String body, String userId, String articleId) {
    this.id = UUID.randomUUID().toString();
    this.body = body;
    this.userId = userId;
    this.articleId = articleId;
    this.createdAt = LocalDateTime.now();
  }
}
