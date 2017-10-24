package com.vilen.realworld.core.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by vilen on 17/10/22.
 */
@Data
@NoArgsConstructor
public class FollowRelation {

    private String userId;
    private String targetId;

    public FollowRelation(String userId,String targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

}
