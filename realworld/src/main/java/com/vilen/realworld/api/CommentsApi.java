package com.vilen.realworld.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.vilen.realworld.api.exception.InvalidRequestException;
import com.vilen.realworld.api.exception.NoAuthorizationException;
import com.vilen.realworld.api.exception.ResourceNotFoundException;
import com.vilen.realworld.application.CommentQueryService;
import com.vilen.realworld.application.data.CommentData;
import com.vilen.realworld.core.article.Article;
import com.vilen.realworld.core.article.ArticleRepository;
import com.vilen.realworld.core.comment.Comment;
import com.vilen.realworld.core.comment.CommentRepository;
import com.vilen.realworld.core.service.AuthorizationService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by vilen on 17/11/01.
 */
@RestController
@RequestMapping(path = "/articles/{slug}/comments")
public class CommentsApi {
    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private CommentQueryService commentQueryService;

    @Autowired
    public CommentsApi(ArticleRepository articleRepository,
                       CommentRepository commentRepository,
                       CommentQueryService commentQueryService) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.commentQueryService = commentQueryService;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable("slug") String slug,
                                           @AuthenticationPrincipal User user,
                                           @Valid @RequestBody NewCommentParam newCommentParam,
                                           BindingResult bindingResult) {
        Article article = findArticle(slug);
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        Comment comment = new Comment(newCommentParam.getBody(), user.getId(), article.getId());
        commentRepository.save(comment);
        return ResponseEntity.status(201).body(commentResponse(commentQueryService.findById(comment.getId(), user).get()));
    }

    @GetMapping
    public ResponseEntity getComments(@PathVariable("slug") String slug,
                                      @AuthenticationPrincipal User user) {
        Article article = findArticle(slug);
        List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("comments", comments);
        }});
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("slug") String slug,
                                        @PathVariable("id") String commentId,
                                        @AuthenticationPrincipal User user) {
        Article article = findArticle(slug);
        return commentRepository.findById(article.getId(), commentId).map(comment -> {
            if (!AuthorizationService.canWriteComment(user, article, comment)) {
                throw new NoAuthorizationException();
            }
            commentRepository.remove(comment);
            return ResponseEntity.noContent().build();
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private Article findArticle(String slug) {
        return articleRepository.findBySlug(slug).map(article -> article).orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> commentResponse(CommentData commentData) {
        return new HashMap<String, Object>() {{
            put("comment", commentData);
        }};
    }
}


@Getter
@NoArgsConstructor
@JsonRootName("comment")
class NewCommentParam {
    @NotBlank(message = "can't be empty")
    private String body;
}
