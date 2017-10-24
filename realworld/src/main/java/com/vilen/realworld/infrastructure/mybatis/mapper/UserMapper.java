package com.vilen.realworld.infrastructure.mybatis.mapper;

import com.vilen.realworld.core.user.FollowRelation;
import com.vilen.realworld.core.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

/**
 * Created by vilen on 2017/10/17.
 */

@Component
@Mapper
public interface UserMapper {
    void insert(@Param("user") User user);

    User findByUsername(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    User findById(@Param("id") String id);

    void update(@Param("user") User user);

    FollowRelation findRelation(@Param("userId") String userId, @Param("targetId") String targetId);

    void saveRelation(@Param("followRelation") FollowRelation followRelation);

    void deleteRelation(@Param("followRelation") FollowRelation followRelation);
}
