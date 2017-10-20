package com.vilen.SpringbootCodeTemplate.controllers;

import com.vilen.SpringbootCodeTemplate.beans.User;
import com.vilen.SpringbootCodeTemplate.config.ServerCfg;
import com.vilen.common.beans.ResultBean;
import com.vilen.common.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by vilen on 17/10/20.
 */
@RestController
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @PostMapping("/login")
    public ResultBean<User> login(HttpSession session, String username) {
        logger.info("login user:" + username);
        // 模拟登陆
        User user = new User(100L, username, "nick");
        session.setAttribute(UserUtil.KEY_USER,user);
        return new ResultBean<User>(user);
    }

    @Autowired
    ServerCfg cfg;

    @GetMapping(value = "/configTest")
    public ResultBean<ServerCfg> configTest() {
        return new ResultBean<ServerCfg>(cfg);
    }

}
