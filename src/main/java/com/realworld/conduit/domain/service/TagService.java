package com.realworld.conduit.domain.service;

import com.realworld.conduit.domain.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
  private final TagRepository tagRepository;
  public List<String> findAll() {
    return tagRepository.findAll();
  }
}
