package com.vilen.SpringbootCodeTemplate.controllers;

import com.vilen.SpringbootCodeTemplate.beans.User;
import com.vilen.SpringbootCodeTemplate.services.UserService;
import com.vilen.common.beans.ResultBean;
import com.vilen.common.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by vilen on 2017/10/18.
 */
@RequestMapping("/app")
@RestController
@CrossOrigin
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResultBean<User> login(HttpSession session, String username, String password) {
        logger.info("login user:" + username);
        User user = userService.login(username, password);
        session.setAttribute(UserUtil.KEY_USER, user);
        return new ResultBean<>(user);
    }

    @PostMapping(value = "/logout")
    public ResultBean<Boolean> logout(HttpSession session) {
        session.invalidate();
        return new ResultBean<Boolean>(true);
    }
}

