package com.vilen.realworld.infrastructure.mybatis.readservice;

import com.vilen.realworld.application.data.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by vilen on 17/10/20.
 */
@Component
@Mapper
public interface UserReadService {

    UserData findByUsername(@Param("username") String username);

    UserData findById(@Param("id") String id);
}
