package com.vilen.realworld.application.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by vilen on 17/10/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String id;
    private String email;
    private String username;
    private String bio;
    private String image;
}
