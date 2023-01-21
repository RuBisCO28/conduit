package com.realworld.conduit.application.resource.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Profile;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@JsonRootName(value = "article")
public class SingleArticleResponse {
  private String slug;
  private String title;
  private String description;
  private String body;
  private List<String> tagList;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean favorited;
  private int favoriteCount;
  @JsonIgnore
  private String articleId;
  private Profile author;

  public static SingleArticleResponse from(ArticleWithSummary article) {
    final var response = new SingleArticleResponse();
    response.setSlug(article.getSlug());
    response.setTitle(article.getTitle());
    response.setDescription(article.getDescription());
    response.setBody(article.getBody());
    response.setTagList(article.getTagList());
    response.setCreatedAt(article.getCreatedAt());
    response.setUpdatedAt(article.getUpdatedAt());
    response.setFavorited(article.isFavorited());
    response.setFavoriteCount(article.getFavoritesCount());
    response.setAuthor(article.getAuthorProfile());
    return response;
  }
}
