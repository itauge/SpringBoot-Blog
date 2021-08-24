package com.itauge.blog.service;

import com.itauge.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment comment);

    List<Comment> listCommentParentIdIsNull(Long id);

    Page<Comment> findAll(Pageable pageable);

    void deleteComment(Long id);
}
