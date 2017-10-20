package com.vilen.realworld.api;

import com.vilen.realworld.api.exception.ResourceNotFoundException;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.data.ArticleData;
import com.vilen.realworld.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vilen on 17/10/20.
 */
@RestController
@RequestMapping(path = "/articles/{slug}")
public class ArticleApi {
    @Autowired
    private ArticleQueryService articleQueryService;

    @GetMapping
    public ResponseEntity<?> article(@PathVariable("slug") String slug,
    User user) {
        return articleQueryService.findBySlug(slug, user)
                .map(articleData -> ResponseEntity.ok(articleResponse(articleData)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> articleResponse(ArticleData articleData) {
        return new HashMap<String, Object>() {{
            put("article", articleData);
        }};
    }
}
