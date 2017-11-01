package com.vilen.realworld.infrastructure.mybatis.mapper;

import com.vilen.realworld.core.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by vilen on 17/11/01.
 */

@Component
@Mapper
public interface CommentMapper {
    void insert(@Param("comment") Comment comment);

    Comment findById(@Param("articleId") String articleId, @Param("id") String id);

    void delete(@Param("id") String id);
}