package com.vilen.realworld.infrastructure.mybatis.readservice;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vilen on 17/10/29.
 */

@Component
@Mapper
public interface TagReadService {
    List<String> all();
}
