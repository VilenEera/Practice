package com.vilen.realworld.core.article;

import java.util.Optional;

/**
 * Created by vilen on 2017/10/17.
 */
public interface ArticleRepository {
    void save(Article article);

    Optional<Article> findById(String id);

    Optional<Article> findBySlug(String slug);

    void remove(Article article);
}
