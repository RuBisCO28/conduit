package com.realworld.conduit.domain.object;

import lombok.Getter;

import java.util.List;

@Getter
public class ArticlesWithCount {
  private final List<Article> articles;

  private final int count;

  public ArticlesWithCount(List<Article> articles, int count) {
    this.articles = articles;
    this.count = count;
  }
}
