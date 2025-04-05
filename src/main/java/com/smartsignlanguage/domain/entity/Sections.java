package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 论坛版块表(Sections)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:08:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sections")
public class Sections {
    @TableId(type = IdType.AUTO)
    private Integer id;

    //版块名称
    private String name;
    //版块描述
    private String description;
    //版主ID，关联users表
    private Integer moderatorId;
    //创建时间
    private Date createdAt;
    // 帖子数量
    private Integer topicCount;
    // 评论数量
    private Integer commentCount;
    // 版块图标URL
    private String iconUrl;
    // 版块状态，1表示正常，0表示异常
    private Integer status;

}

