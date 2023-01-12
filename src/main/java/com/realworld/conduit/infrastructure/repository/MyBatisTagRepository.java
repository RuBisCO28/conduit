package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.repository.TagRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.TagMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisTagRepository implements TagRepository {
  private final TagMapper tagMapper;

  @Override
  @Transactional
  public List<String> findAll() {
    return tagMapper.findAll();
  }
}
