package com.itauge.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;

    public boolean isRecommend(){
        return recommend;
    }

    public void setRecommend(boolean recommend){
        this.recommend = recommend;
    }
}
