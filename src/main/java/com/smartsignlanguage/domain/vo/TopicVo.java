package com.smartsignlanguage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicVo {

    private Integer id;
    //主题标题
    private String title;
    //主题内容
    private String content;
    //发帖用户ID
    private Integer userId;
    //发帖用户名(增)
    private String username;
    // 发帖用户头像(增)
    private String avatar;
    //所属版块ID
    private Integer sectionId;
    //所属版块名(增)
    private String sectionName;
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
    // 是否收藏(0为未收藏)
    private Integer isFavorites;
    // 附件地址
    private String attachmentUrl;
    // 附件名字
    private String attachmentName;

}
