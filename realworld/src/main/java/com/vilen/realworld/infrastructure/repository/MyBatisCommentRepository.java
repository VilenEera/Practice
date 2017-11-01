package com.vilen.realworld.infrastructure.repository;

import com.vilen.realworld.core.comment.Comment;
import com.vilen.realworld.core.comment.CommentRepository;
import com.vilen.realworld.infrastructure.mybatis.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by vilen on 17/11/01.
 */
@Component
public class MyBatisCommentRepository implements CommentRepository {
    private CommentMapper commentMapper;

    @Autowired
    public MyBatisCommentRepository(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public Optional<Comment> findById(String articleId, String id) {
        return Optional.ofNullable(commentMapper.findById(articleId, id));
    }

    @Override
    public void remove(Comment comment) {
        commentMapper.delete(comment.getId());
    }
}
