package com.realworld.conduit.domain.repository;

import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Page;
import java.util.List;

public interface ArticleRepository {
  void save(Article article);
  List<String> fetchIdsByQuery(String tag, String author, Page page);
  int countByTagAndAuthor(String tag, String author);
  List<ArticleWithSummary> findAllByIds(List<String> articleIds);
  List<ArticleWithSummary> findArticlesOfAuthors(List<String> authors, Page page);
  int countFeedSize(List<String> authors);
}
