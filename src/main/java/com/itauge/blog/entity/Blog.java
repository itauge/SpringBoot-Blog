package com.itauge.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareState;
    private boolean comment;
    private boolean publish;
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;
    //cascadeType.PERSIST 級聯新增
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    /**
     * 博客描述
     * */
    private String description;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
    /**
     * 这个属性不需要保存到数据库，仅做为查询标签的条件
     * */
    @Transient
    private String tagIds;

    /**
     * 初始化标签字符串，controller层调用该方法后前端渲染
     * */
    public void initTags(){
        this.tagIds = parseTags(this.tags);
    }
    /**
     * 为了前端显示多标签，在这里将List转化为"1,2,3"形式的字符串
     * 这样前端就会自动显示多个对应ID的标签
     * @return：tagid组成的字符串/空list
     * */
    private String parseTags(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        else {
            return tagIds;
        }
    }
}
