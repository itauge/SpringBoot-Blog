package com.itauge.blog.service.Impl;

import com.itauge.blog.dao.CommentRepository;
import com.itauge.blog.entity.Comment;
import com.itauge.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    /**
     * 存放递归迭代出来的所有评论
     * */
    private List<Comment> replys = new ArrayList<>();

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        Page<Comment> all = commentRepository.findAll(pageable);
        return all;
    }


    @Override
    public List<Comment> listCommentParentIdIsNull(Long id) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.getByBlogIdAndParentCommentIsNull(id, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getById(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循環每個頂級的評論節點
     */
    public List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments){
            Comment tmp = new Comment();
            BeanUtils.copyProperties(comment,tmp);
            commentList.add(tmp);
        }
        //合并評論的各層子代到第一級子代集合中
        combineChilidren(commentList);
        return commentList;
    }

    /**
     * 獲取頂層對象的所有子評論
     */
    public void combineChilidren(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> tmp = comment.getReplyComments();
            for (Comment reply : tmp){
                getReplys(reply);
            }
            //設置當前父評論的子評論
            comment.setReplyComments(this.replys);
            //清空集合,繼續找到父評論的子評論
            replys = new ArrayList<>();
        }
    }

    /**
     * 存放所有迭代出的評論入list
     */
    private void getReplys(Comment comment){
        //添加根節點
        replys.add(comment);
        //如果有子節點
        if (comment.getReplyComments().size() > 0){
            //遞歸獲得子節點
            List<Comment> list = comment.getReplyComments();
            for (Comment comment1 : list){
                getReplys(comment1);
            }
        }
    }




}
