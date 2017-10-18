package com.vilen.SpringbootCodeTemplate.daos;

import com.vilen.SpringbootCodeTemplate.beans.Config;

import java.util.Collection;

/**
 * Created by vilen on 2017/10/18.
 */
public interface ConfigDao {
    Collection<Config> getAll();

    long add(Config config);

    boolean delete(long id);
}
