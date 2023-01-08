package com.realworld.conduit.application.resource.comment;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.Data;

@Data
@JsonRootName(value = "comments")
public class MultipleCommentsResponse {
  List<SingleCommentResponse> comments;

  public static MultipleCommentsResponse from(List<SingleCommentResponse> comments) {
    final var response = new MultipleCommentsResponse();
    response.setComments(comments);
    return response;
  }
}
