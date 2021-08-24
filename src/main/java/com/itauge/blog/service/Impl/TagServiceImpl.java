package com.itauge.blog.service.Impl;

import com.itauge.blog.dao.BlogRepository;
import com.itauge.blog.dao.TagRepository;
import com.itauge.blog.entity.Blog;
import com.itauge.blog.entity.Tag;
import com.itauge.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(Long id) {
        List<Blog> blogs = tagRepository.getOne(id).getBlogs();
        for (Blog blog : blogs) {
            blog.setTags(null);
        }
        tagRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagRepository.getById(id);

        //将tag的所有属性赋值给one
        BeanUtils.copyProperties(tag, one);

        return tagRepository.save(one);
    }

    @Override
    public Page<Tag> list(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getByName(name);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    /**
     * 将获得的字符串转为list，以作为查询条件
     * */
    private List<Long> getId(String ids){

        List<Long> id = new ArrayList<>();

        if(ids!=null & !("".equals(ids))){
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                id.add(Long.valueOf(s));
            }
        }
        return id;
    }

    @Override
    public List<Tag> getTagsById(String ids) {
        List<Long> id = getId(ids);

        return tagRepository.findAllById(id);
    }

    @Override
    public int getIdByName(String name) {
        return tagRepository.getIdByName(name);
    }

    /**
     * 通过分页显示标签下blog数目最多的，由大到小
     * */
    @Override
    public List<Tag> getTop(Integer size) {
        Sort orders = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, orders);

        //获取全部
        List<Tag> top = tagRepository.findTop(pageable);
        //存放是发布状态的博客列表
        List<Blog> blogs = new ArrayList<>();
        //新的列表
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : top) {
            for(Blog blog : tag.getBlogs()){
                if(blog.isPublish()){
                    blogs.add(blog);
                }
            }
            if(blogs.size()!=0){
                tag.setBlogs(blogs);
                tags.add(tag);
            }
            //清空
            blogs = new ArrayList<>();
        }
        return tags;
    }


}
