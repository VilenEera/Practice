package com.vilen.realworld.infrastructure.repository;

import com.vilen.realworld.core.favorite.ArticleFavorite;
import com.vilen.realworld.core.favorite.ArticleFavoriteRepository;
import com.vilen.realworld.infrastructure.mybatis.mapper.ArticleFavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by vilen on 17/10/29.
 */
@Repository
public class MyBatisArticleFavoriteRepository implements ArticleFavoriteRepository {
    private ArticleFavoriteMapper mapper;

    @Autowired
    public MyBatisArticleFavoriteRepository(ArticleFavoriteMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void save(ArticleFavorite articleFavorite) {
        if (mapper.find(articleFavorite.getArticleId(), articleFavorite.getUserId()) == null) {
            mapper.insert(articleFavorite);
        }
    }

    @Override
    public Optional<ArticleFavorite> find(String articleId, String userId) {
        return Optional.ofNullable(mapper.find(articleId, userId));
    }

    @Override
    public void remove(ArticleFavorite favorite) {
        mapper.delete(favorite);
    }
}