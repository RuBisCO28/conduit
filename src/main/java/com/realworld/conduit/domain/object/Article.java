package com.realworld.conduit.domain.object;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Article {
  private String id;
  private String userId;
  private String title;
  private String slug;
  private String description;
  private String body;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Article(
    String title,
    String description,
    String body,
    String userId) {
    this.id = UUID.randomUUID().toString();
    this.userId = userId;
    this.title = title;
    this.slug = toSlug(title);
    this.description = description;
    this.body = body;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public static String toSlug(String title) {
    return title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
  }
}
