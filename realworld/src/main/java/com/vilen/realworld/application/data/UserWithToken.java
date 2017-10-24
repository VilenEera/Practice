package com.vilen.realworld.application.data;

import lombok.Getter;

/**
 * Created by vilen on 17/10/24.
 */
@Getter
public class UserWithToken {
    private String email;
    private String username;
    private String bio;
    private String image;
    private String token;

    public UserWithToken(UserData userData, String token) {
        this.email = userData.getEmail();
        this.username = userData.getUsername();
        this.bio = userData.getBio();
        this.image = userData.getImage();
        this.token = token;
    }

}
