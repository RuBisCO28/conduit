package com.realworld.conduit.application.resource.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.Author;
import com.realworld.conduit.domain.object.Profile;
import com.realworld.conduit.domain.object.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
  private Author author;

  public static SingleArticleResponse from(Article article,
                                           List<Tag> tags,
                                           boolean isFavorited,
                                           int favoriteCount,
                                           Profile profile) {
    final var response = new SingleArticleResponse();
    response.setSlug(article.getSlug());
    response.setTitle(article.getTitle());
    response.setDescription(article.getDescription());
    response.setBody(article.getBody());
    response.setTagList(tags.stream().map(Tag::getName).toList());
    response.setCreatedAt(article.getCreatedAt());
    response.setUpdatedAt(article.getUpdatedAt());
    response.setFavorited(isFavorited);
    response.setFavoriteCount(favoriteCount);
    response.setAuthor(new Author(profile));
    return response;
  }
}
