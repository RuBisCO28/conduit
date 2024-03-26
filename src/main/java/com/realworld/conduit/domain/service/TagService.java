package com.realworld.conduit.domain.service;

import com.realworld.conduit.domain.object.Tag;
import com.realworld.conduit.infrastructure.mybatis.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
  private final TagMapper tagMapper;

  @Transactional
  public List<String> findAll() {
    return tagMapper.findAll();
  }

  public List<Tag> findByArticleId(String articleId) {
    return tagMapper.findByArticleId(articleId);
  }
}
