package com.realworld.conduit.application.resource.article;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagedArticlesRequest {
  @NotBlank(message = "can't be empty")
  private String offset;
  @NotBlank(message = "can't be empty")
  private String limit;
  private String tag;
  private String author;
  private String favoritedBy;
}
