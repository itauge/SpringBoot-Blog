package com.itauge.blog.service;

import com.itauge.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag save(Tag tag);

    Tag getTag(Long id);

    void deleteTag(Long id);

    Tag updateTag(Long id, Tag tag);

    Page<Tag> list(Pageable pageable);

    Tag getByName(String name);

    List<Tag> getAll();

    List<Tag> listTagTop(Integer size);

    List<Tag> getTagsById(String ids);

    int getIdByName(String name);

    List<Tag> getTop(Integer size);
}
