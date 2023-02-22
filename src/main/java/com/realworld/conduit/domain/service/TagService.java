package com.realworld.conduit.domain.service;

import com.realworld.conduit.infrastructure.mybatis.mapper.TagMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {
  private final TagMapper tagMapper;

  @Transactional
  public List<String> findAll() {
    return tagMapper.findAll();
  }
}
