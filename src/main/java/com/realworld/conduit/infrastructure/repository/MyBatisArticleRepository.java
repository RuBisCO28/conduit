package com.realworld.conduit.infrastructure.repository;

import com.realworld.conduit.domain.object.Article;
import com.realworld.conduit.domain.object.ArticleWithSummary;
import com.realworld.conduit.domain.object.Page;
import com.realworld.conduit.domain.object.Tag;
import com.realworld.conduit.domain.repository.ArticleRepository;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.ArticleTagMapper;
import com.realworld.conduit.infrastructure.mybatis.mapper.TagMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MyBatisArticleRepository implements ArticleRepository {
  private final ArticleMapper articleMapper;
  private final ArticleTagMapper articleTagMapper;
  private final TagMapper tagMapper;

  @Override
  @Transactional
  public void save(Article article) {
    for (Tag tag : article.getTags()) {
      Tag targetTag = tagMapper.findByName(tag.getName());
      if (targetTag == null) {
        tagMapper.insert(tag);
        Tag newTag = tagMapper.findByName(tag.getName());
        articleTagMapper.insert(article.getId(), newTag.getId());
      }
    }
    articleMapper.insert(article);
  }

  @Override
  @Transactional
  public List<String> fetchIdsByQuery(String tag, String author, Page page) {
    return articleMapper.fetchIdsByQuery(tag, author, page);
  }

  @Override
  @Transactional
  public int countByTagAndAuthor(String tag, String author) {
    return articleMapper.countByTagAndAuthor(tag, author);
  }

  @Override
  @Transactional
  public List<ArticleWithSummary> findAllByIds(List<String> articleIds) {
    return articleMapper.findAllByIds(articleIds);
  }

  @Override
  @Transactional
  public List<ArticleWithSummary> findArticlesOfAuthors(List<String> authors, Page page) {
    return articleMapper.findArticlesOfAuthors(authors, page);
  }

  @Override
  @Transactional
  public int countFeedSize(List<String> authors) {
    return articleMapper.countFeedSize(authors);
  }
}
