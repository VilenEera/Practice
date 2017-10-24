package com.vilen.realworld.core.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by vilen on 2017/10/16.
 */
@Repository
public interface UserRepository {
    void save(User user);

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void saveRelation(FollowRelation followRelation);

    Optional<FollowRelation> findRelation(String userId, String targetId);

    void removeRelation(FollowRelation followRelation);
}
