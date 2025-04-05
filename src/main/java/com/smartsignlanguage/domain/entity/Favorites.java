package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 帖子收藏表(Favorites)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:07:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("favorites")
public class Favorites  {
    @TableId(type = IdType.AUTO)
    private Integer id;

//用户ID
    private Integer userId;
//主题ID
    private Integer topicId;
//收藏时间
    private Date createdAt;



}

