package com.smartsignlanguage.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentVo {
    // 评论id
    private Integer id;
    //回复内容
    private String content;
    //回复用户ID
    private Integer userId;
    //所属主题ID
    private Integer topicId;
    //回复时间
    private Date createdAt;
    // 用户名
    private String username;
    // 头像
    private String avatar;

}
