package com.itauge.blog.dao;

import com.itauge.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b WHERE b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    /**
     * ?1代表传递第一个参数到该位置
     * select * from Blog where title like "%内容%";
     * "%内容%"代表使用模糊查询，Query中使用?1不会帮我们加上%，因此需要在controller中手动加上
     * */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    void updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year")
    List<String> findGroupByYears();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> listByYear(String year);
}
