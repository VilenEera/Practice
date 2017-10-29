package com.vilen.realworld.application;

import com.vilen.realworld.infrastructure.mybatis.readservice.TagReadService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vilen on 17/10/29.
 */
@Service
public class TagsQueryService {
    private TagReadService tagReadService;

    public TagsQueryService(TagReadService tagReadService) {
        this.tagReadService = tagReadService;
    }

    public List<String> allTags() {
        return tagReadService.all();
    }
}