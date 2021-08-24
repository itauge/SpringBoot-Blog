package com.itauge.blog.service;

import com.itauge.blog.entity.Blog;
import com.itauge.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    //將數據庫content轉成html形式，方便md輸出
    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> searchBlog(String query,Pageable pageable);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listRecommedBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

}
