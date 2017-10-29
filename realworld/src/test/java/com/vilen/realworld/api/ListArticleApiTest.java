package com.vilen.realworld.api;

import com.vilen.realworld.JacksonCustomizations;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.Page;
import com.vilen.realworld.application.data.ArticleDataList;
import com.vilen.realworld.core.article.ArticleRepository;
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

import static com.vilen.realworld.TestHelper.articleDataFixture;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.Arrays.asList;
import static java.util.Arrays.equals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/29.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ArticlesApi.class)
@Import({WebSecurityConfig.class, JacksonCustomizations.class})
public class ListArticleApiTest extends TestWithCurrentUser {
    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleQueryService articleQueryService;

    @Autowired
    private MockMvc mvc;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_get_default_article_list() throws Exception {
        ArticleDataList articleDataList = new ArticleDataList(
                asList(articleDataFixture("1",user),articleDataFixture("2",user)),2
        );
        when(articleQueryService.findRecentArticles(eq(null), eq(null), eq(null), eq(new Page(0, 20)), eq(null)))
                .thenReturn(articleDataList);
        RestAssuredMockMvc.when()
                .get("/articles")
                .prettyPeek()
                .then().statusCode(200);
    }

    @Test
    public void should_get_feeds_401_without_login() throws Exception {
        RestAssuredMockMvc.when()
                .get("/articles/feed")
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @Test
    public void should_get_feeds_success() throws Exception {
        ArticleDataList articleDataList = new ArticleDataList(
                asList(articleDataFixture("1", user), articleDataFixture("2", user)), 2);
        when(articleQueryService.findUserFeed(eq(user), eq(new Page(0, 20)))).thenReturn(articleDataList);

        given()
                .header("Authorization", "Token " + token)
                .when()
                .get("/articles/feed")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
