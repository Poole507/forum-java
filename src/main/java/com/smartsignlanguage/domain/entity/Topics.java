package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 主题帖子表(Topics)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:08:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("topics")
public class Topics  {
    @TableId(type = IdType.AUTO)
    private Integer id;

//主题标题
    private String title;
//主题内容
    private String content;
//发帖用户ID
    private Integer userId;
//所属版块ID
    private Integer sectionId;
//浏览次数
    private Integer viewCount;
//回复数量
    private Integer replyCount;
//发帖时间
    private Date createdAt;
//最后更新时间
    private Date updatedAt;
//帖子状态：1-正常，0-删除
    private Integer status;



}

