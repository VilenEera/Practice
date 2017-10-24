package com.vilen.realworld.api;

import com.vilen.realworld.JacksonCustomizations;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.UserQueryService;
import com.vilen.realworld.application.data.UserData;
import com.vilen.realworld.core.service.JwtService;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserReadService;
import com.vilen.realworld.infrastructure.service.NaiveEncryptService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;


/**
 * Created by vilen on 17/10/24.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UsersApi.class)
@Import({WebSecurityConfig.class, UserQueryService.class,
        NaiveEncryptService.class, JacksonCustomizations.class})
public class UsersApiTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserReadService userReadService;

    private String defaultAvatar;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
        defaultAvatar = "https://static.productionready.io/images/smiley-cyrus.jpg";
    }

    @Test
    public void should_create_user_success() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob";

        when(jwtService.toToken(any())).thenReturn("123");
        User user = new User(email, username, "123", "", defaultAvatar);
        UserData userData = new UserData(user.getId(), email, username, "", defaultAvatar);
        when(userReadService.findById(any())).thenReturn(userData);

        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.empty());

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given()
                .contentType("application/json")
                .body(param)
                .post("/users")
                .then().statusCode(201)
                .body("user.email", equalTo(email))
                .body("user.username", equalTo(username))
                .body("user.bio", equalTo(""))
                .body("user.image", equalTo(defaultAvatar))
                .body("user.token", equalTo("123"));

        verify(userRepository).save(any());

    }

    private HashMap<String, Object> prepareRegisterParameter(final String email, final String username) {
        return new HashMap<String, Object>() {{
            put("user", new HashMap<String, Object>() {{
                put("email", email);
                put("password", "johnnyjacob");
                put("username", username);
            }});
        }};
    }

    @Test
    public void should_show_error_message_for_blank_username() throws Exception{
        String email = "john@jacob.com";
        String username = "";
        Map<String, Object> param = prepareRegisterParameter(email, username);
        given()
                .contentType("application/json")
                .body(param)
                .when()
                .post("/users")
                .then()
                .statusCode(422)
                .body("errors.username[0]", equalTo("can't be empty"));
    }
}
