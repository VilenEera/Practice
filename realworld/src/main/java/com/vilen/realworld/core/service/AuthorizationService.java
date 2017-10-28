package com.vilen.realworld.core.service;

import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.comment.Comment;
import com.vilen.realworld.core.user.User;

/**
 * Created by vilen on 17/10/29.
 */
public class AuthorizationService {
    public static boolean canWriteArticle(User user, Article article) {
        return user.getId().equals(article.getUserId());
    }

    public static boolean canWriteComment(User user, Article article, Comment comment) {
        return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
    }
}
