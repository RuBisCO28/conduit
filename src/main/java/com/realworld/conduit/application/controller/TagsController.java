package com.realworld.conduit.application.controller;

import com.realworld.conduit.application.resource.tag.TagsResponse;
import com.realworld.conduit.domain.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tags")
public class TagsController {
  private final TagService tagService;
  @GetMapping
  public ResponseEntity getTags() {
    return ResponseEntity.ok(TagsResponse.from(tagService.findAll()));
  }
}
