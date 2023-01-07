package com.realworld.conduit.application.resource.article;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName(value = "article")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
  private String title;
  private String description;
  private String body;
}
