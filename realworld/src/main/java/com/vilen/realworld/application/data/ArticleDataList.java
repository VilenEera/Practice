package com.vilen.realworld.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Created by vilen on 17/10/29.
 */
@Getter
public class ArticleDataList {
    @JsonProperty("articles")
    private final List<ArticleData> articleDatas;
    @JsonProperty("articlesCount")
    private final int count;

    public ArticleDataList(List<ArticleData> articleDatas, int count) {

        this.articleDatas = articleDatas;
        this.count = count;
    }
}
