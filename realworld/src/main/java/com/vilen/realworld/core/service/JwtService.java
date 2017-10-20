package com.vilen.realworld.core.service;

import com.vilen.realworld.core.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by vilen on 17/10/20.
 */
@Service
public interface JwtService {
    String toToken(User user);

    Optional<String> getSubFromToken(String token);
}
