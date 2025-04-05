package com.smartsignlanguage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartsignlanguage.domain.dto.AddTopicsDto;
import com.smartsignlanguage.domain.dto.CommentDto;
import com.smartsignlanguage.domain.dto.PageDto;
import com.smartsignlanguage.domain.dto.QuaryTopicBySectionIdDto;
import com.smartsignlanguage.domain.entity.Topics;
import com.smartsignlanguage.domain.vo.CommentVo;
import com.smartsignlanguage.domain.vo.TopicVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smartsignlanguage.result.Result;

import java.util.List;


/**
 * 主题帖子表(Topics)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 01:17:00
 */
public interface TopicsService extends IService<Topics> {

    IPage<TopicVo> getAllTopics(PageDto pageDto);

    /**
     * 分页获取当前用户的主题帖子
     * @param pageDto
     * @return 分页后的主题帖子列表
     */
    IPage<TopicVo> getTopicsByUserId(PageDto pageDto,Integer userId);

    /**
     * 分页获取指定板块的主题帖子
     * @param sectionIdDto 包含板块ID和分页参数的DTO
     * @return 分页后的主题帖子列表
     */
    IPage<TopicVo> getTopicsBySectionId(QuaryTopicBySectionIdDto sectionIdDto);

    TopicVo getTopicById(Integer topicId);

    Result addTopic(AddTopicsDto addTopicsDto);

    List<TopicVo> searchTopics(String search);

    Result addComment(CommentDto commentDto);

    List<CommentVo> getComments(Integer topicId);

    Result collectTopic(Integer topicId);

    Result cancelCollect(Integer topicId);

    IPage<TopicVo> getMyCollect(PageDto pageDto);

    Result getTopicInfoNum();

    Result removeFromTheShelf(Integer topicId);

    Result putOnTheShelf(Integer topicId);

    Result removeTopic(Integer topicId);
}

