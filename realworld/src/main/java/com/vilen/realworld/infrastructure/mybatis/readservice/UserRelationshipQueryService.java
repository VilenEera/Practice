package com.vilen.realworld.infrastructure.mybatis.readservice;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by vilen on 17/10/22.
 */
@Component
@Mapper
public interface UserRelationshipQueryService {
    boolean isUserFollowing(@Param("userId") String userId, @Param("anotherUserId") String anotherUserId);

    Set<String> followingAuthors(@Param("userId") String userId, @Param("ids") List<String> ids);

    List<String> followedUsers(@Param("userId") String userId);
}
