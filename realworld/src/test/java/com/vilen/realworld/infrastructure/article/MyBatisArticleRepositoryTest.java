package com.vilen.realworld.infrastructure.article;

import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.article.Tag;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisArticleRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vilen on 2017/10/17.
 */
@MybatisTest
@RunWith(SpringRunner.class)
@Import({MyBatisArticleRepository.class, MyBatisUserRepository.class})
public class MyBatisArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private Article article;

    @Before
    public void setUp()throws Exception{
        User user = new User("vilen@gmail.com", "vilen", "123", "bio", "default");
        userRepository.save(user);
        article = new Article(user.getId(), "test", "desc", "body", new String[]{"java", "spring"});
    }

    @Test
    public void should_create_and_fetch_article_success() throws Exception {
        articleRepository.save(article);
        Optional<Article> optional = articleRepository.findbyId(article.getId());
        assertThat(optional.isPresent(), is(true));
        assertThat(optional.get(),is(article));
        assertThat(optional.get().getTags().contains(new Tag("java")), is(true));
        assertThat(optional.get().getTags().contains(new Tag("spring")),is(true));
    }
}
