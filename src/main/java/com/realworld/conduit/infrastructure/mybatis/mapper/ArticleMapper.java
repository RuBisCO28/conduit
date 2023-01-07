package com.realworld.conduit.infrastructure.mybatis.mapper;

import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Page;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
  void insert(@Param("article") Article article);
  List<String> fetchIdsByQuery(@Param("tag") String tag, @Param("author") String author, @Param("page") Page page);
  int countByTagAndAuthor(
    @Param("tag") String tag,
    @Param("author") String author);

  List<ArticleWithSummary> findAllByIds(@Param("articleIds") List<String> articleIds);
  List<ArticleWithSummary> findArticlesOfAuthors(@Param("authors") List<String> authors, @Param("page") Page page);
  int countFeedSize(@Param("authors") List<String> authors);
  ArticleWithSummary findBySlug(@Param("slug") String slug);
  void update(@Param("article") ArticleWithSummary article);
  void delete(@Param("article") ArticleWithSummary article);
}
