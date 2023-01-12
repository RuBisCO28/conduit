package com.realworld.conduit.application.resource.tag;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.Data;

@Data
@JsonRootName(value = "tags")
public class TagsResponse {
  private List<String> tags;

  public static TagsResponse from(List<String> tags) {
    final var response = new TagsResponse();
    response.setTags(tags);
    return response;
  }
}
