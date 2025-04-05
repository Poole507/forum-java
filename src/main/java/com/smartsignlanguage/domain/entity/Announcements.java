package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 论坛公告表(Announcements)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:07:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("announcements")
public class Announcements  {
    @TableId(type = IdType.AUTO)
    private Integer id;

//公告标题
    private String title;
//公告内容
    private String content;
//发布用户ID
    private Integer userId;
//发布时间
    private Date createdAt;
//最后更新时间
    private Date updatedAt;
//公告状态：1-正常，0-下架
    private Integer status;



}

