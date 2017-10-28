package com.vilen.realworld.api;

import com.vilen.realworld.api.exception.ResourceNotFoundException;
import com.vilen.realworld.application.ProfileQueryService;
import com.vilen.realworld.application.data.ProfileData;
import com.vilen.realworld.core.user.FollowRelation;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by vilen on 17/10/27.
 */
@RestController
@RequestMapping(path = "profiles/{username}")
public class ProfileApi {
    private ProfileQueryService profileQueryService;
    private UserRepository userRepository;

    @Autowired
    public ProfileApi(ProfileQueryService profileQueryService, UserRepository userRepository) {
        this.profileQueryService = profileQueryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity getProfile(@PathVariable("username") String username,
                                     @AuthenticationPrincipal User user) {
        return profileQueryService.findByUsername(username, user)
                .map(this::profileResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private ResponseEntity profileResponse(ProfileData profile) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("profile", profile);
        }});
    }

    @PostMapping(path = "follow")
    public ResponseEntity follow(@PathVariable("username") String username,
                                 @AuthenticationPrincipal User user){
        return userRepository.findByUsername(username).map(target -> {
            FollowRelation followRelation = new FollowRelation(user.getId(), target.getId());
            userRepository.saveRelation(followRelation);
            return profileResponse(profileQueryService.findByUsername(username, user).get());
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping(path = "follow")
    public ResponseEntity unfollow(@PathVariable("username") String username,
                                   @AuthenticationPrincipal User user) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User target = userOptional.get();
            return userRepository.findRelation(user.getId(), target.getId())
                    .map(relation -> {
                        userRepository.removeRelation(relation);
                        return profileResponse(profileQueryService.findByUsername(username, user).get());
                    }).orElseThrow(ResourceNotFoundException::new);
        } else{
            throw new ResourceNotFoundException();
        }
    }
}

