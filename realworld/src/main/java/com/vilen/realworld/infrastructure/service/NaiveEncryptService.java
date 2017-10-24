package com.vilen.realworld.infrastructure.service;

import com.vilen.realworld.core.user.EncryptService;
import org.springframework.stereotype.Service;

/**
 * Created by vilen on 17/10/24.
 */
@Service
public class NaiveEncryptService implements EncryptService {
    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public boolean check(String checkPassword, String realPassword) {
        return checkPassword.equals(realPassword);
    }
}
