package com.itauge.blog.dao;

import com.itauge.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag getByName(String name);
    int getIdByName(String name);

    /**
     * 通过分页查询拥有blog数目最多的tag
     * @param pageable : 分页
     * */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
