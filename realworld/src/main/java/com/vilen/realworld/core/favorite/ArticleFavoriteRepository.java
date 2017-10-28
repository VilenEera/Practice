package com.vilen.realworld.core.favorite;

import java.util.Optional;

/**
 * Created by vilen on 17/10/29.
 */
public interface ArticleFavoriteRepository {
    void save(ArticleFavorite articleFavorite);

    Optional<ArticleFavorite> find(String articleId, String userId);

    void remove(ArticleFavorite favorite);
}