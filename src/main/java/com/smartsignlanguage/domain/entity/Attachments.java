package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 附件信息表(Attachments)表实体类
 *
 * @author makejava
 * @since 2025-04-01 23:07:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("attachments")
public class Attachments  {
    @TableId(type = IdType.AUTO)
    private Integer id;

    //文件名
    private String filename;
    //文件存储路径
    private String filepath;
    //关联的主题ID
    private Integer topicId;
    //关联的回复ID
    private Integer postId;
    //上传用户ID
    private Integer userId;
    //上传时间
    private Date createdAt;


}

