package com.vilen.realworld.api;

import com.vilen.realworld.application.data.UserData;
import com.vilen.realworld.core.service.JwtService;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserReadService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by vilen on 17/10/20.
 */
public abstract class TestWithCurrentUser {
    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected UserReadService userReadService;

    protected User user;
    protected UserData userData;
    protected String token;
    protected String email;
    protected String username;
    protected String defaultAvatar;

    @MockBean
    protected JwtService jwtService;

    protected void userFixture() {
        email = "john@jacob.com";
        username = "johnjacob";
        defaultAvatar = "https://static.productionready.io/images/smiley-cyrus.jpg";

        user = new User(email, username, "123", "", defaultAvatar);
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(user));
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        userData = new UserData(user.getId(), email, username, "", defaultAvatar);
        when(userReadService.findById(eq(user.getId()))).thenReturn(userData);

        token = "token";
        when(jwtService.getSubFromToken(eq(token))).thenReturn(Optional.of(user.getId()));
    }

    @Before
    public void setUp() throws Exception {
        userFixture();
    }
}
