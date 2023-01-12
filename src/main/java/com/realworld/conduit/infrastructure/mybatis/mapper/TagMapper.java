package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Tag;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagMapper {
  Tag findByName(@Param("name") String name);

  void insert(@Param("tag") Tag tag);

  List<String> findAll();
}
