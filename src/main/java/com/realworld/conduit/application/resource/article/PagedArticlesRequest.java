package com.realworld.conduit.application.resource.article;

import java.util.Optional;
import lombok.Data;

@Data
public class PagedArticlesRequest {
  private String offset;
  private String limit;
  private String tag;
  private String author;
}
