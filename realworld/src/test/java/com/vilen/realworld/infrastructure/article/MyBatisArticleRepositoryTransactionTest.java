package com.vilen.realworld.infrastructure.article;

import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.mybatis.mapper.ArticleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by vilen on 2017/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MyBatisArticleRepositoryTransactionTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void transactional_test() throws Exception {
        User user = new User("vilen@gmail.com","vilen","123","bio","default");
        userRepository.save(user);
        Article article = new Article(user.getId(), "test", "desc", "body", new String[]{"java", "spring"});
        articleRepository.save(article);
        Article anotherArticle = new Article(user.getId(), "test", "desc", "body", new String[]{"java", "spring", "other"});
        boolean flag = false;
        try {
            articleRepository.save(anotherArticle);
        } catch (Exception e) {
            flag = true;
            assertThat(articleMapper.findTag("java"),is(true));
            assertThat(articleMapper.findTag("other"), is(false));
        }
        assertTrue(flag);
    }
}
