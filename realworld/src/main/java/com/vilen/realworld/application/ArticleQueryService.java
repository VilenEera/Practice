package com.vilen.realworld.application;

import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.infrastructure.mybatis.readservice.ArticleReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by vilen on 17/10/20.
 */
@Service
public class ArticleQueryService {
    @Autowired
    private ArticleReadService articleReadService;

    public Optional<ArticleData> findById(String id, User user) {
        ArticleData articleData = articleReadService.findById(id);
        if (articleData == null) {
            return Optional.empty();
        } else {
            if (user != null) {
//                fillExtraInfo(id, user, articleData);
            }
            return Optional.of(articleData);
        }
    }

    public Optional<ArticleData> findBySlug(String slug, User user) {
        ArticleData articleData = articleReadService.findBySlug(slug);
        if (articleData == null) {
            return Optional.empty();
        } else {
            if (user != null) {
                //fillExtraInfo(articleData.getId(), user, articleData);
            }
            return Optional.of(articleData);
        }
    }

//    private void fillExtraInfo(List<ArticleData> articles, User currentUser) {
//        setFavoriteCount(articles);
//        if (currentUser != null) {
//            setIsFavorite(articles, currentUser);
//            setIsFollowingAuthor(articles, currentUser);
//        }
//    }
//
//    private void setIsFollowingAuthor(List<ArticleData> articles, User currentUser) {
//        Set<String> followingAuthors = userRelationshipQueryService.followingAuthors(
//                currentUser.getId(),
//                articles.stream().map(articleData1 -> articleData1.getProfileData().getId()).collect(toList()));
//        articles.forEach(articleData -> {
//            if (followingAuthors.contains(articleData.getProfileData().getId())) {
//                articleData.getProfileData().setFollowing(true);
//            }
//        });
//    }
//
//    private void setFavoriteCount(List<ArticleData> articles) {
//        List<ArticleFavoriteCount> favoritesCounts = articleFavoritesReadService.articlesFavoriteCount(articles.stream().map(ArticleData::getId).collect(toList()));
//        Map<String, Integer> countMap = new HashMap<>();
//        favoritesCounts.forEach(item -> {
//            countMap.put(item.getId(), item.getCount());
//        });
//        articles.forEach(articleData -> articleData.setFavoritesCount(countMap.get(articleData.getId())));
//    }
//
//    private void setIsFavorite(List<ArticleData> articles, User currentUser) {
//        Set<String> favoritedArticles = articleFavoritesReadService.userFavorites(articles.stream().map(articleData -> articleData.getId()).collect(toList()), currentUser);
//
//        articles.forEach(articleData -> {
//            if (favoritedArticles.contains(articleData.getId())) {
//                articleData.setFavorited(true);
//            }
//        });
//    }
//
//    private void fillExtraInfo(String id, User user, ArticleData articleData) {
//        articleData.setFavorited(articleFavoritesReadService.isUserFavorite(user.getId(), id));
//        articleData.setFavoritesCount(articleFavoritesReadService.articleFavoriteCount(id));
//        articleData.getProfileData().setFollowing(
//                userRelationshipQueryService.isUserFollowing(
//                        user.getId(),
//                        articleData.getProfileData().getId()));
//    }
}
