package com.vilen.realworld.infrastructure.mybatis.mapper;

import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by vilen on 2017/10/17.
 */
@Component
@Mapper
public interface ArticleMapper {
    void insert(@Param("article") Article article);

    Article findById(@Param("id") String id);

    boolean findTag(@Param("tagName") String tagName);

    void insertTag(@Param("tag") Tag tag);

    void insertArticleTagRelation(@Param("articleId") String articleId, @Param("tagId") String tagId);

    Article findBySlug(@Param("slug") String slug);

    void update(@Param("article") Article article);

    void delete(@Param("id") String id);
}
