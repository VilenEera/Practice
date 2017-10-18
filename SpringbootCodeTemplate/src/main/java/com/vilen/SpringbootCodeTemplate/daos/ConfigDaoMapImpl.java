package com.vilen.SpringbootCodeTemplate.daos;

import com.vilen.SpringbootCodeTemplate.beans.Config;
import com.vilen.common.utils.UserUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.vilen.common.utils.CheckUtil.check;

/**
 * Created by vilen on 2017/10/18.
 */
@Component
public class ConfigDaoMapImpl implements ConfigDao {

    private final ConcurrentSkipListMap<Long, Config> configs = new ConcurrentSkipListMap<>();
    private static final AtomicLong idSequence = new AtomicLong(1000L);

    @PostConstruct
    public void init() {

    }

    @Override
    public Collection<Config> getAll() {
        return configs.values();
    }

    @Override
    public long add(Config config) {
        //检查名称是否重复
        check(null == getByName(config.getName()),"name.repeat");
        //创建用户
        config.setCreator(UserUtil.getUserId());
        long id = idSequence.incrementAndGet();
        config.setId(id);
        configs.put(id, config);
        return id;
    }

    private Config getByName(String name) {
        Collection<Config> values = configs.values();
        for (Config config : values) {
            if (config.getName().equalsIgnoreCase(name)) {
                return config;
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        Config config = configs.get(id);
        if (config==null) {
            return false;
        }
        // 判断是否可以删除
        check(canDelete(config), "no.permission");
        return configs.remove(id) != null;
    }

    /**
     * 判断逻辑变化可能性大，抽取一个函数
     * @param config
     */
    private boolean canDelete(Config config) {
        return UserUtil.getUserId() == config.getCreator();
    }
}
