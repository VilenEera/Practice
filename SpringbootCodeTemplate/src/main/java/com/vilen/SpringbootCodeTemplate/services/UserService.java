package com.vilen.SpringbootCodeTemplate.services;

import com.vilen.SpringbootCodeTemplate.beans.User;
import com.vilen.common.exceptions.CheckException;
import com.vilen.common.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by vilen on 2017/10/18.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User login(String username, String password) {
        //todo 测试代码
        if ("chhx".equals(username) && "chhx".equals(password)) {
            User user = new User(1000L, username, "姓名刘");
            return user;
        }
        throw new CheckException("请用户名和密码都输入chhx");
    }
}
