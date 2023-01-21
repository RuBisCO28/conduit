package com.realworld.conduit.domain.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithSummary {
  private String id;
  private String slug;
  private String title;
  private String description;
  private String body;
  private boolean favorited;
  private int favoritesCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<String> tagList;
  @JsonProperty("author")
  private Profile authorProfile;

  public ArticleWithSummary(Article article, Profile profile, boolean favorited, int favoriteCount) {
    this.id = article.getId();
    this.slug = article.getSlug();
    this.title = article.getTitle();
    this.description = article.getDescription();
    this.body = article.getBody();
    this.favorited = favorited;
    this.favoritesCount = favoriteCount;
    this.createdAt = article.getCreatedAt();
    this.updatedAt = article.getUpdatedAt();
    this.tagList = article.getTags().stream().map(Tag::getName).collect(Collectors.toList());
    this.authorProfile = profile;
  }
}
