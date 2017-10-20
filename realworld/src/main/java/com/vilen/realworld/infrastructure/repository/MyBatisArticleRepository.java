package com.vilen.realworld.infrastructure.repository;

import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.article.Tag;
import com.vilen.realworld.infrastructure.mybatis.mapper.ArticleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by vilen on 2017/10/17.
 */
@Repository
public class MyBatisArticleRepository implements ArticleRepository {

    private ArticleMapper articleMapper;

    public MyBatisArticleRepository(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    @Transactional
    public void save(Article article) {
        if (articleMapper.findById(article.getId()) == null) {
            createNew(article);
        } else {
            articleMapper.update(article);
        }
    }

    private void createNew(Article article) {
        for (Tag tag : article.getTags()) {
            if (!articleMapper.findTag(tag.getName())) {
                articleMapper.insertTag(tag);
            }
            articleMapper.insertArticleTagRelation(article.getId(),tag.getId());
        }
        articleMapper.insert(article);
    }

    @Override
    public Optional<Article> findbyId(String id) {
        return Optional.ofNullable(articleMapper.findById(id));
    }

    @Override
    public Optional<Article> findbySlug(String slug) {
        return Optional.ofNullable(articleMapper.findBySlug(slug));
    }

    @Override
    public void remove(Article article) {
        articleMapper.delete(article.getId());
    }
}
