package com.vilen.SpringbootCodeTemplate.services;

import com.vilen.SpringbootCodeTemplate.beans.Config;
import com.vilen.SpringbootCodeTemplate.daos.ConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.vilen.common.utils.CheckUtil.check;
import static com.vilen.common.utils.CheckUtil.notEmpty;
import static com.vilen.common.utils.CheckUtil.notNull;

/**
 * 配置业务处理类
 * Created by vilen on 2017/10/18.
 */
@Service
public class ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);
    @Autowired
    ConfigDao dao;

    public Collection<Config> getAll() {
        // 校验通过后打印重要的日志
        logger.info("getAll start ...");
        Collection<Config> data = dao.getAll();
        logger.info("getAll end,data size:" + data.size());
        return data;
    }

    public long add(Config config) {
        //参数校验
        notNull(config, "param.is.null");
        notEmpty(config.getName(), "name.is.null");
        notEmpty(config.getValue(), "value.is.null");

        //校验通过后打印重要的日志
        logger.info("add config:" + config);
        long newId = dao.add(config);
        //修改操作需要打印操作结果
        logger.info("add config success,id:" + newId);
        return newId;
    }

    public boolean delete(long id) {
        check(id >0L,"id.error",id);
        boolean result = dao.delete(id);
        logger.info("delete config success,id:" + id + ", result:" + result);
        return dao.delete(id);
    }

    public boolean delete2(long id) {
        // 示例代码
        int userType = getCurrentUserType();
        logger.info("delete config,id:" + id + ", userType: " + userType);
        boolean result = false;
        if (userType == 1) {
            // do something
        } else {

        }
        logger.info("delete config success,id:" + id + ", result:" + result);
        return result;
    }

    private int getCurrentUserType() {
        return 2;
    }
}
