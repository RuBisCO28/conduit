package com.realworld.conduit.domain.object;

import java.util.List;
import lombok.Getter;

@Getter
public class ArticlesWithCount {
  private final List<ArticleWithSummary> articles;

  private final int count;

  public ArticlesWithCount(List<ArticleWithSummary> articles, int count) {
    this.articles = articles;
    this.count = count;
  }
}
