package com.vilen.realworld.core.favorite;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by vilen on 17/10/29.
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ArticleFavorite {
    private String articleId;
    private String userId;

    public ArticleFavorite(String articleId, String userId) {
        this.articleId = articleId;
        this.userId = userId;
    }
}