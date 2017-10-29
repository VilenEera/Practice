package com.vilen.realworld.api;

import com.vilen.realworld.JacksonCustomizations;
import com.vilen.realworld.TestHelper;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.user.User;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/20.
 */
@RunWith(SpringRunner.class)
@WebMvcTest({ArticleApi.class})
@Import({WebSecurityConfig.class, JacksonCustomizations.class})
public class ArticleApiTest extends TestWithCurrentUser{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleQueryService articleQueryService;

    @MockBean
    private ArticleRepository articleRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_read_article_success() throws Exception {
        String slug = "test-new-article";
        DateTime time = new DateTime();
        Article article = new Article("Test New Article", "Desc", "Body", new String[]{"java", "spring", "jpg"}, user.getId(), time);
        ArticleData articleData = TestHelper.getArticleDataFromArticleAndUser(article, user);

        when(articleQueryService.findBySlug(eq(slug), eq(null))).thenReturn(Optional.of(articleData));

        RestAssuredMockMvc.when()
                .get("/articles/{slug}", slug)
                .then()
                .statusCode(200)
                .body("article.slug", equalTo(slug))
                .body("article.body", equalTo(articleData.getBody()))
                .body("article.createdAt", equalTo(ISODateTimeFormat.dateTime().withZoneUTC().print(time)));
    }

    @Test
    public void should_404_if_article_not_found() throws Exception {
        when(articleQueryService.findBySlug(anyString(), any())).thenReturn(Optional.empty());
        RestAssuredMockMvc.when()
                .get("/articles/not-exists")
                .then()
                .statusCode(404);
    }

    @Test
    public void should_update_article_content_success() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";

        Map<String, Object> updateParam = prepareUpdateParam(title, body, description);
        Article article = new Article(title, description, body, new String[]{"java", "spring", "jpg"}, user.getId());
        ArticleData articleData = TestHelper.getArticleDataFromArticleAndUser(article, user);
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        when(articleQueryService.findBySlug(eq(article.getSlug()), eq(user))).thenReturn(Optional.of(articleData));
        given().contentType("application/json")
                .header("Authorization", "Token " + token)
                .body(updateParam)
                .when()
                .put("/articles/{slug}", article.getSlug())
                .then()
                .statusCode(200)
                .body("article.slug", equalTo(articleData.getSlug()));
    }

    private HashMap<String, Object> prepareUpdateParam(final String title, final String body, final String description) {
        return new HashMap<String, Object>() {{
            put("article", new HashMap<String, Object>() {{
                put("title", title);
                put("body", body);
                put("description", description);
            }});
        }};
    }

    @Test
    public void should_get_403_if_not_author_to_update_article() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";
        Map<String, Object> updateParam = prepareUpdateParam(title, body, description);

        User anotherUser = new User("test@test.com", "test", "123123", "", "");
        Article article = new Article(title,description,body,new String[]{"java","spring","jpg"},anotherUser.getId());
        DateTime time = new DateTime();
        ArticleData articleData = new ArticleData( article.getId(),
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                false,
                0,
                time,
                time,
                Arrays.asList("joda"),
                new ProfileData(anotherUser.getId(), anotherUser.getUsername(), anotherUser.getBio(), anotherUser.getImage(), false));
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        when(articleQueryService.findBySlug(eq(article.getSlug()), eq(user))).thenReturn(Optional.of(articleData));
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + token)
                .body(updateParam).when()
                .put("/articles/{slug}", article.getSlug())
                .then().statusCode(403);
    }

    @Test
    public void should_delete_article_success() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";
        Article article = new Article(title, description, body, new String[]{"java", "spring", "jpg"}, user.getId());
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        given()
                .header("Authorization", "Token " + token)
                .when()
                .delete("/articles/{slug}", article.getSlug())
                .then().statusCode(204);
        verify(articleRepository).remove(eq(article));
    }

    @Test
    public void should_403_if_not_author_delete_article() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";
        User anotherUser = new User("test@test.com", "test", "123123", "", "");
        Article article = new Article(title, description, body, new String[]{"java", "spring", "jpg"}, anotherUser.getId());

        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        given()
                .header("Authorization", "Token " + token)
                .when()
                .delete("/articles/{slug}", article.getSlug())
                .then()
                .statusCode(403);
    }
}
