package com.vilen.realworld.application.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by vilen on 17/10/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileData {
    @JsonIgnore
    private String id;
    private String username;
    private String bio;
    private String image;
    private boolean following;
}