package com.vilen.realworld.core.comment;

import java.util.Optional;

/**
 * Created by vilen on 17/11/01.
 */
public interface  CommentRepository {
    void save(Comment comment);

    Optional<Comment> findById(String articleId, String id);

    void remove(Comment comment);
}