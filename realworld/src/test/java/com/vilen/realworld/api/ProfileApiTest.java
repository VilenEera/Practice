package com.vilen.realworld.api;

import com.vilen.realworld.JacksonCustomizations;
import com.vilen.realworld.api.security.WebSecurityConfig;
import com.vilen.realworld.application.ProfileQueryService;
import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.core.user.FollowRelation;
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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/27.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProfileApi.class)
@Import({WebSecurityConfig.class, JacksonCustomizations.class})
public class ProfileApiTest extends TestWithCurrentUser {
    private User anotherUser;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileQueryService profileQueryService;
    private ProfileData profileData;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
        anotherUser = new User("username@test.com", "username", "123", "", "");
        profileData = new ProfileData(anotherUser.getId(), anotherUser.getUsername(), anotherUser.getBio(),
                anotherUser.getImage(), false);
        when(userRepository.findByUsername(eq(anotherUser.getUsername()))).thenReturn(Optional.of(anotherUser));
    }

    @Test
    public void should_get_user_profile_success() throws Exception {
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(null)))
                .thenReturn(Optional.of(profileData));
        RestAssuredMockMvc.when()
                .get("/profiles/{username}", profileData.getUsername())
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("profile.username", equalTo(profileData.getUsername()));
    }

    @Test
    public void should_follow_user_success() throws Exception {
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(user))).thenReturn(Optional.of(profileData));
        given()
                .header("Authorization", "Token " + token)
                .when()
                .post("/profiles/{username}/follow", anotherUser.getUsername())
                .prettyPeek()
                .then()
                .statusCode(200);
        verify(userRepository).saveRelation(new FollowRelation(user.getId(),anotherUser.getId()));
    }

    @Test
    public void should_unfollow_user_success() throws Exception {
        FollowRelation followRelation = new FollowRelation(user.getId(), anotherUser.getId());
        when(userRepository.findRelation(eq(user.getId()), eq(anotherUser.getId()))).thenReturn(Optional.of(followRelation));
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(user))).thenReturn(Optional.of(profileData));

        given()
                .header("Authorization", "Token " + token)
                .when()
                .delete("/profiles/{username}/follow", anotherUser.getUsername())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
