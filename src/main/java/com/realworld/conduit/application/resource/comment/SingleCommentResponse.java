package com.realworld.conduit.application.resource.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.realworld.conduit.domain.object.Comment;
import com.realworld.conduit.domain.object.Profile;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonRootName(value = "comment")
public class SingleCommentResponse {
  private String id;
  private String body;
  @JsonIgnore
  private String articleId;
  private Profile author;
  private LocalDateTime createdAt;

  public static SingleCommentResponse from(Comment comment, Profile authorProfile) {
    final var response = new SingleCommentResponse();
    response.setId(comment.getId());
    response.setBody(comment.getBody());
    response.setAuthor(authorProfile);
    response.setCreatedAt(comment.getCreatedAt());
    return response;
  }
}
