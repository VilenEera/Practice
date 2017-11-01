package com.vilen.realworld.application;

import com.vilen.realworld.application.data.CommentData;
import com.vilen.realworld.core.user.User;
import com.vilen.realworld.infrastructure.mybatis.readservice.CommentReadService;
import com.vilen.realworld.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by vilen on 17/11/01.
 */
@Service
public class CommentQueryService {
    private CommentReadService commentReadService;
    private UserRelationshipQueryService userRelationshipQueryService;

    public CommentQueryService(CommentReadService commentReadService, UserRelationshipQueryService userRelationshipQueryService) {
        this.commentReadService = commentReadService;
        this.userRelationshipQueryService = userRelationshipQueryService;
    }

    public Optional<CommentData> findById(String id, User user) {
        CommentData commentData = commentReadService.findById(id);
        if (commentData == null) {
            return Optional.empty();
        } else {
            commentData.getProfileData().setFollowing(
                    userRelationshipQueryService.isUserFollowing(
                            user.getId(),
                            commentData.getProfileData().getId()));
        }
        return Optional.ofNullable(commentData);
    }

    public List<CommentData> findByArticleId(String articleId, User user) {
        List<CommentData> comments = commentReadService.findByArticleId(articleId);
        if (comments.size() > 0) {
            Set<String> followingAuthors = userRelationshipQueryService.followingAuthors(user.getId(), comments.stream().map(commentData -> commentData.getProfileData().getId()).collect(Collectors.toList()));
            comments.forEach(commentData -> {
                if (followingAuthors.contains(commentData.getProfileData().getId())) {
                    commentData.getProfileData().setFollowing(true);
                }
            });
        }
        return comments;
    }
}

