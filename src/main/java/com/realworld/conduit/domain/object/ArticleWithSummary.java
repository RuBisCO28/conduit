package com.realworld.conduit.domain.object;

import java.time.LocalDateTime;
import java.util.List;
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
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<String> tagList;
}
