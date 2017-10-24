package com.vilen.realworld.application;

import com.vilen.realworld.application.data.UserData;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserReadService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by vilen on 17/10/24.
 */
@Service
public class UserQueryService {
    private UserReadService userReadService;

    public UserQueryService(UserReadService userReadService) {
        this.userReadService = userReadService;
    }

    public Optional<UserData> findById(String id) {
        return Optional.ofNullable(userReadService.findById(id));
    }
}
