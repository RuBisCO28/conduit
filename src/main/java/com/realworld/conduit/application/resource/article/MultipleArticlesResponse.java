package com.realworld.conduit.application.resource.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
@Data
public class MultipleArticlesResponse {

  @JsonProperty("articles")
  List<SingleArticleResponse> articles;

  @JsonProperty("articlesCount")
  int count;

  public static MultipleArticlesResponse from(List<SingleArticleResponse> articles, int count) {
    final var response = new MultipleArticlesResponse();
    response.setArticles(articles);
    response.setCount(count);
    return response;
  }
}
