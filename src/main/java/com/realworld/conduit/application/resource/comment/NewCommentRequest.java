package com.realworld.conduit.application.resource.comment;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName(value = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentRequest {
  @NotBlank(message = "can't be empty")
  private String body;
}
