package com.vilen.realworld;

import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.user.User;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vilen on 17/10/20.
 */
public class TestHelper {
    public static ArticleData articleDataFixture(String seed, User user) {
        DateTime now = new DateTime();
        return new ArticleData(
                seed + "id",
                "title-" + seed,
                "title " + seed,
                "desc " + seed,
                "body " + seed, false, 0, now, now, new ArrayList<>(),
                new ProfileData(user.getId(), user.getUsername(), user.getBio(), user.getImage(), false));
    }

    public static ArticleData getArticleDataFromArticleAndUser(Article article, User user) {
        return new ArticleData(
                article.getId(),
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                false,
                0,
                article.getCreatedAt(),
                article.getUpdatedAt(),
                Arrays.asList("joda"),
                new ProfileData(user.getId(), user.getUsername(), user.getBio(), user.getImage(), false));
    }
}
