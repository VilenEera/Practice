package com.vilen.realworld.infrastructure.comment;

import com.vilen.realworld.core.comment.Comment;
import com.vilen.realworld.core.comment.CommentRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisCommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by vilen on 17/11/01.
 */
@MybatisTest
@RunWith(SpringRunner.class)
@Import({MyBatisCommentRepository.class})
public class MyBatisCommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void should_create_and_fetch_comment_success() throws Exception {
        Comment comment = new Comment("content", "123", "456");
        commentRepository.save(comment);

        Optional<Comment> optional = commentRepository.findById("456", comment.getId());
        assertThat(optional.isPresent(), is(true));
        assertThat(optional.get(), is(comment));
    }
}
