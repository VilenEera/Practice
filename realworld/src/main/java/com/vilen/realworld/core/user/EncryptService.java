package com.vilen.realworld.core.user;

/**
 * Created by vilen on 17/10/22.
 */
public interface EncryptService {
    String encrypt(String password);

    boolean check(String checkPassword, String realPassword);
}
