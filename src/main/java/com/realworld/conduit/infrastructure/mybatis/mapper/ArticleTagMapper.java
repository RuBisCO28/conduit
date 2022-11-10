package com.realworld.conduit.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleTagMapper {
  void insert(@Param("articleId") String articleId, @Param("tagId") String tagId);
}
