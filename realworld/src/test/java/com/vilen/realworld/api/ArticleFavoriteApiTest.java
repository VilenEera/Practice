package com.vilen.realworld.api;

import com.vilen.realworld.JacksonCustomizations;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.article.Tag;
import com.vilen.realworld.core.favorite.ArticleFavorite;
import com.vilen.realworld.core.favorite.ArticleFavoriteRepository;
import com.vilen.realworld.core.user.User;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/29.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ArticleFavoriteApi.class)
@Import({WebSecurityConfig.class, JacksonCustomizations.class})
public class ArticleFavoriteApiTest extends TestWithCurrentUser {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleFavoriteRepository articleFavoriteRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleQueryService articleQueryService;

    private Article article;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
        User anotherUser = new User("other@test.com", "other", "123", "", "");
        article = new Article("title", "desc", "body", new String[]{"java"}, anotherUser.getId());
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        ArticleData articleData = new ArticleData(
                article.getId(),
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                true,
                1,
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getTags().stream().map(Tag::getName).collect(Collectors.toList()),
                new ProfileData(
                        anotherUser.getId(),
                        anotherUser.getUsername(),
                        anotherUser.getBio(),
                        anotherUser.getImage(),
                        false
                ));
        when(articleQueryService.findBySlug(eq(articleData.getSlug()), eq(user))).thenReturn(Optional.of(articleData));
    }

    @Test
    public void should_favorite_an_article_success() throws Exception {
        given()
                .header("Authorization", "Token " + token)
                .when()
                .post("/articles/{slug}/favorite", article.getSlug())
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("article.id", equalTo(article.getId()));
        verify(articleFavoriteRepository).save(any());
    }

    @Test
    public void should_unfavorite_an_article_success() throws Exception {
        when(articleFavoriteRepository.find(eq(article.getId()), eq(user.getId()))).thenReturn(
                Optional.of(new ArticleFavorite(article.getId(), user.getId())));
        given()
                .header("Authorization", "Token " + token)
                .when()
                .delete("/articles/{slug}/favorite", article.getSlug())
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("article.id", equalTo(article.getId()));
        verify(articleFavoriteRepository).remove(new ArticleFavorite(article.getId(), user.getId()));
    }
}
