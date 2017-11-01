package com.vilen.realworld.application.comment;

import com.vilen.realworld.application.CommentQueryService;
import com.vilen.realworld.application.data.CommentData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.comment.Comment;
import com.vilen.realworld.core.comment.CommentRepository;
import com.vilen.realworld.core.user.FollowRelation;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisArticleRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisCommentRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by vilen on 17/11/01.
 */
@MybatisTest
@RunWith(SpringRunner.class)
@Import({MyBatisCommentRepository.class, MyBatisUserRepository.class, CommentQueryService.class, MyBatisArticleRepository.class})
public class CommentQueryServiceTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentQueryService commentQueryService;

    @Autowired
    private ArticleRepository articleRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("aisensiy@test.com", "aisensiy", "123", "", "");
        userRepository.save(user);
    }

    @Test
    public void should_read_comment_success() throws Exception {
        Comment comment = new Comment("content",user.getId(),"123");
        commentRepository.save(comment);

        Optional<CommentData> optional = commentQueryService.findById(comment.getId(), user);
        assertThat(optional.isPresent(), is(true));
        CommentData commentData = optional.get();
        assertThat(commentData.getProfileData().getUsername(),is(user.getUsername()));
    }

    @Test
    public void should_read_comments_of_article() throws Exception {
        Article article = new Article("title", "desc", "body", new String[]{"java"}, user.getId());
        articleRepository.save(article);

        User user2 = new User("user2@email.com","user2","123","","");
        userRepository.save(user2);
        userRepository.saveRelation(new FollowRelation(user.getId(),article.getId()));

        Comment comment1 = new Comment("content1", user.getId(), article.getId());
        commentRepository.save(comment1);
        Comment comment2 = new Comment("content2", user2.getId(), article.getId());
        commentRepository.save(comment2);

        List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
        assertThat(comments.size(), is(2));

    }
}
