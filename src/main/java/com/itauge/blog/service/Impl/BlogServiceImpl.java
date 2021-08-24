package com.itauge.blog.service.Impl;

import com.itauge.blog.dao.BlogRepository;
import com.itauge.blog.dao.UserRepository;
import com.itauge.blog.entity.Blog;
import com.itauge.blog.entity.Type;
import com.itauge.blog.exception.NotFoundException;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.util.MarkdownUtils;
import com.itauge.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getById(id);
        if (blog == null){
            throw new NotFoundException("blog is doesn't exist");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //更新瀏覽次數
        blogRepository.updateViews(id);

        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            /**
             * 处理动态组合查询分页
             * criteriaQuery：条件容器
             * criteriaBuilder：模糊查询
             * */
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(!("".equals(blog.getTitle())) && blog.getTitle() != null){
                    //模糊查询,泛型指定通过属性"title"获得的数据类型是什么
                    list.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    list.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    list.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //where查询
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        }, pageable);

    }

    @Override
    public Page<Blog> searchBlog(String query,Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
      return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listRecommedBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupByYears();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogRepository.listByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog saveBlog(Blog blog) {
        //如果Id等于null说明是新增，在这时初始化创建时间和浏览次数
        if("".equals(blog.getFlag())){
            blog.setFlag("原創");
        }
        if(blog.getId()==null){
            //初始化blog部分属性
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //浏览次数
            blog.setViews(0);
            //作者
            blog.setUser(userRepository.getById(1L));
            //发布/保存
            blog.setPublish(blog.isPublish());
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog byId = blogRepository.getById(id);
        if ( byId == null){
            throw new NotFoundException("blog doesnt exist");
        }
        if("".equals(blog.getTitle())){
            throw new NotFoundException("標題不能爲空");
        }

        BeanUtils.copyProperties(blog,byId,"createTime");
        byId.setUpdateTime(new Date());
        return blogRepository.save(byId);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


}
