package com.vilen.realworld.application;

import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.application.data.UserData;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserReadService;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by vilen on 17/10/27.
 */
@Component
public class ProfileQueryService {
    private UserReadService userReadService;
    private UserRelationshipQueryService userRelationshipQueryService;

    @Autowired
    public ProfileQueryService(UserReadService userReadService, UserRelationshipQueryService userRelationshipQueryService) {
        this.userReadService = userReadService;
        this.userRelationshipQueryService = userRelationshipQueryService;
    }

    public Optional<ProfileData> findByUsername(String username, User currentUser) {
        UserData userData = userReadService.findByUsername(username);
        if (userData == null) {
            return Optional.empty();
        } else {
            ProfileData profileData = new ProfileData(
                    userData.getId(),
                    userData.getUsername(),
                    userData.getBio(),
                    userData.getImage(),
                    userRelationshipQueryService.isUserFollowing(currentUser.getId(), userData.getId())
            );
            return Optional.of(profileData);
        }
    }
}
