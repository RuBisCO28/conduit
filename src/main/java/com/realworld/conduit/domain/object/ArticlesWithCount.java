package com.realworld.conduit.domain.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticlesWithCount {
  @JsonProperty("articles")
  private final List<ArticleWithSummary> articles;

  @JsonProperty("articlesCount")
  private final int count;

  public ArticlesWithCount(List<ArticleWithSummary> articles, int count) {
    this.articles = articles;
    this.count = count;
  }
}
