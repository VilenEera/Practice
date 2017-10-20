package com.vilen.realworld.infrastructure.mybatis.readservice;

import com.vilen.realworld.application.Page;
import com.vilen.realworld.application.data.ArticleData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vilen on 17/10/20.
 */
@Component
@Mapper
public interface ArticleReadService {
    ArticleData findById(@Param("id") String id);

    ArticleData findBySlug(@Param("slug") String slug);

    List<String> queryArticles(@Param("tag") String tag, @Param("author") String author, @Param("favoritedBy") String favoritedBy, @Param("page") Page page);

    int countArticle(@Param("tag") String tag, @Param("author") String author, @Param("favoritedBy") String favoritedBy);

    List<ArticleData> findArticles(@Param("articleIds") List<String> articleIds);

    List<ArticleData> findArticlesOfAuthors(@Param("authors") List<String> authors, @Param("page") Page page);

    int countFeedSize(@Param("authors") List<String> authors);
}
