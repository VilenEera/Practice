package com.vilen.realworld.api;

import com.vilen.realworld.TestHelper;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/20.
 */
@RunWith(SpringRunner.class)
@WebMvcTest({ArticleApi.class})
@Import({WebSecurityConfig.class})
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
        Article article = new Article(user.getId(),"Test New Article", "Desc", "Body", new String[]{"java", "spring", "jpg"}, time);
        ArticleData articleData = TestHelper.getArticleDataFromArticleAndUser(article, user);

        when(articleQueryService.findBySlug(eq(slug), eq(null))).thenReturn(Optional.of(articleData));

        RestAssuredMockMvc.when()
                .get("/articles/{slug}", slug)
                .then()
                .statusCode(200)
                .body("article.slug", equalTo(slug))
                .body("article.body", equalTo(articleData.getBody()))
//                .body("article.createdAt", equalTo(ISODateTimeFormat.dateTime().withZoneUTC().print(time)))
        ;

    }
}
