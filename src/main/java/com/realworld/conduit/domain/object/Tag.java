package com.realworld.conduit.domain.object;

import java.util.UUID;
import lombok.Data;

@Data
public class Tag {
  private String id;
  private String name;

  public Tag(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }
}
