package com.vilen.realworld.infrastructure.user;

import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
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
import static org.junit.Assert.*;

/**
 * Created by vilen on 2017/10/17.
 */
@RunWith(SpringRunner.class)
@MybatisTest
@Import(MyBatisUserRepository.class)
public class MyBatisUserMapperTest {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @Before
    public void setUp() throws Exception{
        user = new User("aisensiy@163.com","aisensiy","123","","default");
    }

    @Test
    public void should_save_and_fetch_user_success() throws Exception {
        userRepository.save(user);
        Optional<User> userOptional = userRepository.findByUsername("aisensiy");
        assertThat(userOptional.get(),is(user));
        Optional<User> userOptional2 = userRepository.findByEmail("aisensiy@163.com");
        assertThat(userOptional2.get(),is(user));
    }
}