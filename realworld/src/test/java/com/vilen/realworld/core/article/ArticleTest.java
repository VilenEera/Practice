package com.vilen.realworld.core.article;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vilen on 2017/10/17.
 */
public class ArticleTest {

    @Test
    public void should_handle_other_language()throws Exception {
        Article article = new Article("123", "中文：标点标题", "desc", "body", new String[]{"java"});
        assertThat(article.getSlug(), is("中文-标点标题"));
    }
}
