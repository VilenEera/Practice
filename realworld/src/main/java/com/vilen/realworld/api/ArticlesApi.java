package com.vilen.realworld.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.vilen.realworld.api.exception.InvalidRequestException;
import com.vilen.realworld.application.ArticleQueryService;
import com.vilen.realworld.application.Page;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Created by vilen on 17/10/29.
 */
@RestController
@RequestMapping(path = "/articles")
public class ArticlesApi {
    private ArticleRepository articleRepository;
    private ArticleQueryService articleQueryService;

    @Autowired
    public ArticlesApi(ArticleRepository articleRepository, ArticleQueryService articleQueryService) {
        this.articleRepository = articleRepository;
        this.articleQueryService = articleQueryService;
    }

    @PostMapping
    public ResponseEntity createArticle(@Valid @RequestBody NewArticleParam newArticleParam,
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        Article article = new Article(
                newArticleParam.getTitle(),
                newArticleParam.getDescription(),
                newArticleParam.getBody(),
                newArticleParam.getTagList(),
                user.getId()
        );
        articleRepository.save(article);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("article", articleQueryService.findById(article.getId(), user).get());
        }});
    }

    @GetMapping(path = "feed")
    public ResponseEntity getFeed(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                  @RequestParam(value = "limit",defaultValue = "20") int limit,
                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(articleQueryService.findUserFeed(user, new Page(offset, limit)));
    }

    @GetMapping
    public ResponseEntity getArticles(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                  @RequestParam(value = "limit",defaultValue = "20") int limit,
                                  @RequestParam(value = "tag",required = false) String tag,
                                  @RequestParam(value = "favorited",required = false) String favoritedBy,
                                  @RequestParam(value = "author",required = false) String author,
                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(articleQueryService.findRecentArticles(tag, author, favoritedBy, new Page(offset, limit)
                , user));
    }
}

@Getter
@JsonRootName("article")
@NoArgsConstructor
class NewArticleParam {
    @NotBlank(message = "can't be empty")
    private String title;
    @NotBlank(message = "can't be empty")
    private String description;
    @NotBlank(message = "can't be empty")
    private String body;
    private String[] tagList;
}
