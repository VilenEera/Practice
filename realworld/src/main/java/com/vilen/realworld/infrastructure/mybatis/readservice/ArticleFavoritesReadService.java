package com.vilen.realworld.infrastructure.mybatis.readservice;

import com.vilen.realworld.application.data.ArticleFavoriteCount;
import com.vilen.realworld.core.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by vilen on 17/10/29.
 */
@Mapper
@Component
public interface ArticleFavoritesReadService {
    boolean isUserFavorite(@Param("userId") String userId, @Param("articleId") String articleId);

    int articleFavoriteCount(@Param("articleId") String articleId);

    List<ArticleFavoriteCount> articlesFavoriteCount(@Param("ids") List<String> ids);

    Set<String> userFavorites(@Param("ids") List<String> ids, @Param("currentUser") User currentUser);
}
