package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 帖子回复表(Posts)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:07:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("posts")
public class Posts  {
    @TableId(type = IdType.AUTO)
    private Integer id;

//回复内容
    private String content;
//回复用户ID
    private Integer userId;
//所属主题ID
    private Integer topicId;
//回复时间
    private Date createdAt;
//回复状态：1-正常，0-删除
    private Integer status;



}

