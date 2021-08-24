package com.itauge.blog.dao;

import com.itauge.blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogId(Long blogId, Sort sort);

    List<Comment> getByBlogIdAndParentCommentIsNull(Long blogId, Sort sort);

    List<Comment> getCommentByParentCommentIs(Long id);

}
