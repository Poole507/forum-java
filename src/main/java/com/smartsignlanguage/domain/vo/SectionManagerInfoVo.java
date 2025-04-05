package com.smartsignlanguage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionManagerInfoVo {
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
    // 今日新帖数量
    private Integer todayTopicCount;
}
