package com.vilen.realworld.application.article;

import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisArticleRepository;
import com.vilen.realworld.infrastructure.repository.MyBatisUserRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by vilen on 17/10/20.
 */
@MybatisTest
@RunWith(SpringRunner.class)
@Import({
        ArticleQueryService.class,
        MyBatisUserRepository.class,
        MyBatisArticleRepository.class
})
public class ArticleQueryServiceTest {
    @Autowired
    private ArticleQueryService articleQueryService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Article article;

    @Before
    public void setUp() throws Exception{
        user = new User("vilen@gmail.com", "vilen", "123", "bio", "image");
        userRepository.save(user);
        article = new Article(user.getId(), "test", "desc", "body", new String[]{"java", "spring"}, new DateTime());
        articleRepository.save(article);
    }

    @Test
    public void should_fetch_article_success() throws Exception {
        Optional<ArticleData> optional = articleQueryService.findById(article.getId(), user);
        assertThat(optional.isPresent(), is(true));
        ArticleData fetched = optional.get();
        assertThat(fetched.getFavoritesCount(), is(0));
        assertThat(fetched.isFavorited(), is(false));
        assertThat(fetched.getCreatedAt(), notNullValue());
        assertThat(fetched.getUpdatedAt(), notNullValue());
        assertThat(fetched.getTagList().contains("java"), is(true));
    }

//    @Test
//    public void should_get_article_with_right_favorite_and_favorite_count() throws Exception {
//        User anotherUser = new User("other@test.com", "other", "123", "", "");
//        userRepository.save(anotherUser);
//        articleFavoriteRepository.save(new ArticleFavorite(article.getId(), anotherUser.getId()));
//
//        ArticleData articleData = queryService.findById(article.getId(), anotherUser).get();
//        assertThat(articleData.getFavoritesCount(), is(1));
//        assertThat(articleData.isFavorited(), is(true));
//    }

}
