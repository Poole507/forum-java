package com.smartsignlanguage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smartsignlanguage.domain.dto.AddTopicsDto;
import com.smartsignlanguage.domain.dto.CommentDto;
import com.smartsignlanguage.domain.dto.PageDto;
import com.smartsignlanguage.domain.dto.QuaryTopicBySectionIdDto;
import com.smartsignlanguage.domain.vo.CommentVo;
import com.smartsignlanguage.domain.vo.TopicVo;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicsController {
    @Autowired
    private TopicsService topicsService;
    /**
     * 获取所有帖子
     * @param pageDto
     * @return
     */
    @GetMapping("/all")
    public Result getAllTopics(PageDto pageDto) {
        IPage<TopicVo> list = topicsService.getAllTopics(pageDto);
        return Result.success(list);
    }
    /**
     * 根据用户id获取帖子
     * @param pageDto
     * @return
     */
    @GetMapping("/byUserId")
    public Result getTopicsByUserId(PageDto pageDto,Integer userId) {
        IPage<TopicVo> list = topicsService.getTopicsByUserId(pageDto,userId);
        return Result.success(list);
    }
    /**
     * 根据板块id获取帖子
     * @param sectionIdDto
     * @return
     */
    @GetMapping("/bySectionId")
    public Result getTopicsBySectionId(QuaryTopicBySectionIdDto sectionIdDto) {
        IPage<TopicVo> topicsBySectionId = topicsService.getTopicsBySectionId(sectionIdDto);
        return Result.success(topicsBySectionId);
    }
    /**
     * 根据id获取帖子
     * @param topicId
     * @return
     */
    @GetMapping("/byTopicId")
    public Result getTopicById(Integer topicId) {
        TopicVo topicVo = topicsService.getTopicById(topicId);
        return Result.success(topicVo);
    }
    /**
     * 添加帖子
     * @param addTopicsDto
     * @return
     */
    @PostMapping("/add")
    public Result addTopic(@ModelAttribute AddTopicsDto addTopicsDto) {
        return topicsService.addTopic(addTopicsDto);
    }
    /**
     * 搜索帖子
     * @param search
     * @return
     */
    @GetMapping("/search")
    public Result searchTopics(String search) {
        List<TopicVo> list = topicsService.searchTopics(search);
        return Result.success(list);
    }

    /**
     * 添加评论
     * @param commentDto
     * @return
     */
    @PostMapping("/comment")
    public Result addComment(@RequestBody CommentDto commentDto) {
        return topicsService.addComment(commentDto);
    }

    /**
     * 获取帖子的评论
     * @param topicId
     * @return
     */
    @GetMapping("/comments")
    public Result getComments(Integer topicId) {
        List<CommentVo> list = topicsService.getComments(topicId);
        return Result.success(list);
    }
    /**
     * 收藏帖子
     */
    @PostMapping("/collect")
    public Result collectTopic(Integer topicId) {
        return topicsService.collectTopic(topicId);
    }
    /**
     * 取消收藏
     */
    @PostMapping("/cancelCollect")
    public Result cancelCollect(Integer topicId) {
        return topicsService.cancelCollect(topicId);
    }
    /**
     * 获取我的收藏帖子
     */
    @GetMapping("/myCollect")
    public Result getMyCollect(PageDto pageDto) {
        IPage<TopicVo> list = topicsService.getMyCollect(pageDto);
        return Result.success(list);
    }

    /**
     * 获取帖子数量信息
     * @return
     */
    @GetMapping("/userTopicNumInfo")
    public Result getTopicInfoNum(){
        return topicsService.getTopicInfoNum();
    }
    /**
     * 下架帖子
     */
    @PostMapping("/removeFromTheShelf")
    public Result removeFromTheShelf(Integer topicId) {
        return topicsService.removeFromTheShelf(topicId);
    }
    /**
     * 上架帖子
     */
    @PostMapping("/putOnTheShelf")
    public Result putOnTheShelf(Integer topicId) {
        return topicsService.putOnTheShelf(topicId);
    }
    /**
     * 删除帖子
     */
    @PostMapping("/delete")
    public Result delete(Integer topicId) {
        return topicsService.removeTopic(topicId);
    }
}
