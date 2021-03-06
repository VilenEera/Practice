package com.vilen.realworld.infrastructure.mybatis.mapper;

import com.vilen.realworld.core.favorite.ArticleFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by vilen on 17/10/29.
 */
@Mapper
@Component
public interface ArticleFavoriteMapper {
    ArticleFavorite find(@Param("articleId") String articleId, @Param("userId") String userId);

    void insert(@Param("articleFavorite") ArticleFavorite articleFavorite);

    void delete(@Param("favorite") ArticleFavorite favorite);
}
