package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.dto.AddTopicsDto;
import com.smartsignlanguage.domain.dto.CommentDto;
import com.smartsignlanguage.domain.dto.PageDto;
import com.smartsignlanguage.domain.dto.QuaryTopicBySectionIdDto;
import com.smartsignlanguage.domain.entity.*;
import com.smartsignlanguage.domain.vo.CommentVo;
import com.smartsignlanguage.domain.vo.TopicVo;
import com.smartsignlanguage.domain.vo.UserTopicNumInfo;
import com.smartsignlanguage.mapper.AttachmentsMapper;
import com.smartsignlanguage.mapper.TopicsMapper;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.FavoritesService;
import com.smartsignlanguage.service.PostsService;
import com.smartsignlanguage.service.TopicsService;
import com.smartsignlanguage.service.AttachmentsService;
import com.smartsignlanguage.utils.AuthUtil;
import com.smartsignlanguage.utils.BeanCopyUtils;
import com.smartsignlanguage.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.util.List;

/**
 * 主题帖子表(Topics)表服务实现类
 *
 * @author makejava
 * @since 2025-04-02 01:17:00
 */
@Service("topicsService")
public class TopicsServiceImpl extends ServiceImpl<TopicsMapper, Topics> implements TopicsService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SectionsServiceImpl sectionService;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private AttachmentsService attachmentsService;
    @Autowired
    private PostsService postService;
    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private AttachmentsMapper attachmentsMapper;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public IPage<TopicVo> getAllTopics(PageDto pageDto) {
        // 创建分页对象
        Page<Topics> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        
        // 构建查询条件，按创建时间降序排序
        LambdaQueryWrapper<Topics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Topics::getCreatedAt);

        // 执行分页查询
        Page<Topics> topicsPage = getBaseMapper().selectPage(page, queryWrapper);
        
        // 创建TopicVo的分页对象
        Page<TopicVo> topicVoPage = new Page<>();
        // 复制分页信息
        BeanUtils.copyProperties(topicsPage, topicVoPage, "records");
        
        // 转换并处理记录
        List<TopicVo> topicVos = BeanCopyUtils.copyBeanList(topicsPage.getRecords(), TopicVo.class);
        for (TopicVo topicVo : topicVos) {
            // 根据userId查询用户信息
            User user = userService.getById(topicVo.getUserId());
            // 根据sectionId查询板块名
            String sectionName = sectionService.getById(topicVo.getSectionId()).getName();
            
            // 设置额外信息
            topicVo.setUsername(user.getUsername());
            topicVo.setSectionName(sectionName);
            topicVo.setAvatar(user.getAvatar());

            // 截取内容
            if (topicVo.getContent().length() > 100) {
                topicVo.setContent(topicVo.getContent().substring(0, 100) + "...");
            }
        }
        
        // 设置转换后的记录
        topicVoPage.setRecords(topicVos);
        
        return topicVoPage;
    }

    @Override
    public IPage<TopicVo> getTopicsByUserId(PageDto pageDto,Integer userId) {
        if(userId==null){
            // 获取当前线程的用户id
            userId = BaseContext.getCurrentId().intValue();
        }
        // 检查传入的分页参数是否正确
        System.out.println("pageNum: " + pageDto.getPageNum() + ", pageSize: " + pageDto.getPageSize());

        // 创建分页对象
        Page<Topics> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Topics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topics::getUserId, userId)
                    // 根据创建时间排序
                   .orderByDesc(Topics::getCreatedAt);
        
        // 执行分页查询
        Page<Topics> topicsPage = getBaseMapper().selectPage(page, queryWrapper);
        
        // 创建TopicVo的分页对象
        Page<TopicVo> topicVoPage = new Page<>();
        // 复制分页信息
        BeanUtils.copyProperties(topicsPage, topicVoPage, "records");
        
        // 转换并处理记录
        List<TopicVo> topicVos = BeanCopyUtils.copyBeanList(topicsPage.getRecords(), TopicVo.class);
        for (TopicVo topicVo : topicVos) {
            // 根据userId查询用户信息
            User user = userService.getById(topicVo.getUserId());
            // 根据sectionId查询板块名
            String sectionName = sectionService.getById(topicVo.getSectionId()).getName();
            
            // 设置额外信息
            topicVo.setUsername(user.getUsername());
            topicVo.setSectionName(sectionName);
            topicVo.setAvatar(user.getAvatar());

            // 截取内容
            if (topicVo.getContent().length() > 100) {
                topicVo.setContent(topicVo.getContent().substring(0, 100) + "...");
            }
        }
        
        // 设置转换后的记录
        topicVoPage.setRecords(topicVos);
        
        return topicVoPage;
    }

    @Override
    public IPage<TopicVo> getTopicsBySectionId(QuaryTopicBySectionIdDto sectionIdDto) {
        // 创建分页对象
        Page<Topics> page = new Page<>(sectionIdDto.getPageNum(), sectionIdDto.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Topics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topics::getSectionId, sectionIdDto.getSectionId());
        queryWrapper.eq(Topics::getStatus, 1);
        
        // 执行分页查询
        Page<Topics> topicsPage = getBaseMapper().selectPage(page, queryWrapper);
        
        // 创建TopicVo的分页对象
        Page<TopicVo> topicVoPage = new Page<>();
        // 复制分页信息
        BeanUtils.copyProperties(topicsPage, topicVoPage, "records");
        
        // 转换并处理记录
        List<TopicVo> topicVos = BeanCopyUtils.copyBeanList(topicsPage.getRecords(), TopicVo.class);
        for (TopicVo topicVo : topicVos) {
            // 根据userId查询用户信息
            User user = userService.getById(topicVo.getUserId());
            // 根据sectionId查询板块名
            String sectionName = sectionService.getById(topicVo.getSectionId()).getName();
            
            // 设置额外信息
            topicVo.setUsername(user.getUsername());
            topicVo.setSectionName(sectionName);
            topicVo.setAvatar(user.getAvatar());

            // 截取内容
            if (topicVo.getContent().length() > 100) {
                topicVo.setContent(topicVo.getContent().substring(0, 100) + "...");
            }
        }
        
        // 设置转换后的记录
        topicVoPage.setRecords(topicVos);
        
        return topicVoPage;
    }

    @Override
    public TopicVo getTopicById(Integer topicId) {
        // 需要获取用户的头像以及用户名
        Topics topics = getById(topicId);
        if (topics != null) {
            TopicVo topicVo = BeanCopyUtils.copyBean(topics, TopicVo.class);
            User user = userService.getById(topics.getUserId());
            topicVo.setUsername(user.getUsername());
            topicVo.setAvatar(user.getAvatar());
            // 检查帖子是否收藏
            if (favoritesService.isFavorites(topicId)) {
                topicVo.setIsFavorites(1);
            } else {
                topicVo.setIsFavorites(0);
            }
            // 添加附件
            LambdaQueryWrapper<Attachments> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Attachments::getTopicId, topicId);
            Attachments attachments = attachmentsMapper.selectOne(queryWrapper);
            if (attachments != null) {
                topicVo.setAttachmentUrl(attachments.getFilepath());
                topicVo.setAttachmentName(attachments.getFilename());
            }
            return topicVo;
        }

        return null;
    }

    @Override
    public Result addTopic(AddTopicsDto addTopicsDto) {
        // 将这个映射到Topics对象中
        Topics topics = BeanCopyUtils.copyBean(addTopicsDto, Topics.class);
        topics.setStatus(1);
        topics.setCreatedAt(new Date());
        topics.setUpdatedAt(new Date());
        topics.setViewCount(0);
        topics.setReplyCount(0);
        topics.setUserId(BaseContext.getCurrentId().intValue());
        
        // 先保存topic获取topicId
        if (!save(topics)) {
            return Result.error("添加主题失败");
        }
        
        MultipartFile file = addTopicsDto.getFile();
        if(file != null){
            try {
                String fileName = file.getOriginalFilename();
                // 生成唯一的文件名
                String uniqueFileName = "file2/" +UUID.randomUUID() + "_" + fileName;
                
                // 将MultipartFile转换为byte[]并上传到阿里云OSS
                byte[] fileBytes = file.getBytes();
                String url = aliOssUtil.upload(fileBytes, uniqueFileName);
                
                // 创建附件记录
                Attachments attachment = new Attachments();
                attachment.setTopicId(topics.getId());
                attachment.setFilename(fileName);
                attachment.setFilepath(url);
                attachment.setCreatedAt(new Date());
                attachment.setUserId(BaseContext.getCurrentId().intValue());
                
                // 保存附件记录
                attachmentsService.save(attachment);
                
                return Result.success("添加成功！");
            } catch (IOException e) {
                log.error("文件上传失败", e);
                return Result.error("文件上传失败");
            }
        }
        
        return Result.success("添加成功！");
    }

    // 搜索帖子接口
    @Override
    public List<TopicVo> searchTopics(String search) {
        LambdaQueryWrapper<Topics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Topics::getContent, search);
        List<Topics> topics = getBaseMapper().selectList(queryWrapper);
        if(topics != null){
            // 转化
            List<TopicVo> topicVos = BeanCopyUtils.copyBeanList(topics, TopicVo.class);
            for (TopicVo topicVo : topicVos) {
                Integer sectionId = topicVo.getSectionId();
                // 板块名
                topicVo.setSectionName(sectionService.getById(sectionId).getName());
                // 头像
                topicVo.setAvatar(userService.getById(topicVo.getUserId()).getAvatar());
                // 设置帖子的发布人
                topicVo.setUsername(userService.getById(topicVo.getUserId()).getUsername());
            }
            return topicVos;
        }
        return null;
    }

    @Override
    public Result addComment(CommentDto commentDto) {
        // 添加评论,转化为post
        Posts comment = BeanCopyUtils.copyBean(commentDto, Posts.class);
        comment.setUserId(BaseContext.getCurrentId().intValue());
        comment.setCreatedAt(new Date());
        if (postService.save(comment)) {
            return Result.success("评论成功");
        }
        return Result.error("评论失败");
    }

    // 根据帖子id获取评论
    @Override
    public List<CommentVo> getComments(Integer topicId) {
        LambdaQueryWrapper<Posts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Posts::getTopicId, topicId);
        List<Posts> posts = postService.list(queryWrapper);
        if ( posts != null) {
            List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(posts, CommentVo.class);
            for (CommentVo commentVo : commentVos) {
                commentVo.setUsername(userService.getById(commentVo.getUserId()).getUsername());
                commentVo.setAvatar(userService.getById(commentVo.getUserId()).getAvatar());
            }
            return commentVos;
        }
        return null;
    }

    // 收藏帖子
    @Override
    public Result collectTopic(Integer topicId) {
        Favorites favorites = new Favorites();
        favorites.setTopicId(topicId);
        favorites.setUserId(BaseContext.getCurrentId().intValue());
        favorites.setCreatedAt(new Date());
        if (favoritesService.save(favorites)) {
            return Result.success("收藏成功");
        }
        return Result.error("收藏失败");
    }
    // 取消收藏
    @Override
    public Result cancelCollect(Integer topicId) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getTopicId, topicId);
        queryWrapper.eq(Favorites::getUserId, BaseContext.getCurrentId().intValue());
        if (favoritesService.remove(queryWrapper)) {
            return Result.success("取消收藏成功");
        }
        return Result.error("取消收藏失败");
    }

    @Override
    public IPage<TopicVo> getMyCollect(PageDto pageDto) {
        Page<Favorites> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        // 根据userId查询收藏的帖子
        int userId = BaseContext.getCurrentId().intValue();
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getUserId, userId);
        Page<Favorites> topicsPage = favoritesService.page(page, queryWrapper);
        List<Favorites> records = topicsPage.getRecords();
        List<TopicVo> topicVos = new ArrayList<>();
        for (Favorites record : records) {
            Integer topicId = record.getTopicId();
            // 格局topicId查询帖子
            TopicVo topicVo = getTopicById(topicId);
            topicVos.add(topicVo);
        }

        // 创建TopicVo的分页对象
        Page<TopicVo> topicVoPage = new Page<>();
        // 复制分页信息
        BeanUtils.copyProperties(topicsPage, topicVoPage, "records");

        for (TopicVo topicVo : topicVos) {
            // 根据userId查询用户信息
            User user = userService.getById(topicVo.getUserId());
            // 根据sectionId查询板块名
            String sectionName = sectionService.getById(topicVo.getSectionId()).getName();

            // 设置额外信息
            topicVo.setUsername(user.getUsername());
            topicVo.setSectionName(sectionName);
            topicVo.setAvatar(user.getAvatar());

            // 截取内容
            if (topicVo.getContent().length() > 100) {
                topicVo.setContent(topicVo.getContent().substring(0, 100) + "...");
            }
        }

        // 设置转换后的记录
        topicVoPage.setRecords(topicVos);
        return topicVoPage;
    }

    @Override
    public Result getTopicInfoNum() {
        // 获取帖子总数，收藏数量，回复帖子数量
        // 获取userId
        Integer userId = BaseContext.getCurrentId().intValue();
        if (userId != null) {
            UserTopicNumInfo userTopicNumInfo = new UserTopicNumInfo();
            LambdaQueryWrapper<Topics> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Topics::getUserId, userId);
            Integer topicCount = getBaseMapper().selectCount(queryWrapper);
            // 获取收藏数量
            LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            favoritesLambdaQueryWrapper.eq(Favorites::getUserId, userId);
            Integer collectCount = favoritesService.getBaseMapper().selectCount(favoritesLambdaQueryWrapper);
            // 获取评论数量
            LambdaQueryWrapper<Posts> postsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            postsLambdaQueryWrapper.eq(Posts::getUserId, userId);
            Integer commentCount = postService.getBaseMapper().selectCount(postsLambdaQueryWrapper);
            userTopicNumInfo.setTopicNum(topicCount);
            userTopicNumInfo.setCollectNum(collectCount);
            userTopicNumInfo.setCommentNum(commentCount);
            return Result.success(userTopicNumInfo);
        }
        return null;
    }
    // 下架帖子
    @Override
    public Result removeFromTheShelf(Integer topicId) {
        String role = authUtil.getRole();
        if (("admin".equals(role))) {
            Topics topics = getById(topicId);
            topics.setStatus(2);
            if (updateById(topics)) {
                return Result.success("下架成功");
            }
        }
        return Result.error("权限不足");
    }

    @Override
    public Result putOnTheShelf(Integer topicId) {
        String role = authUtil.getRole();
        if (("admin".equals(role))) {
            Topics topics = getById(topicId);
            topics.setStatus(1);
            if (updateById(topics)) {
                return Result.success("上架成功");
            }
        }
        return Result.error("权限不足");
    }

    @Override
    public Result removeTopic(Integer topicId) {
        String role = authUtil.getRole();
        if (("admin".equals(role))) {
            // 更新status状态为0
            Topics topics = getById(topicId);
            topics.setStatus(0);
            updateById(topics);
            return Result.success("删除成功");
        }else if (("moderator".equals(role))) {
            // 查看该文章是否是自己管理的板块
            Integer sectionId = getBaseMapper().selectById(topicId).getSectionId();
            if (sectionService.getBaseMapper().selectById(sectionId).getModeratorId() != BaseContext.getCurrentId().intValue()) {
                return Result.error("权限不足");
            }
            // 更新status状态为0
            Topics topics = getById(topicId);
            topics.setStatus(0);
            updateById(topics);
            return Result.success("删除成功");
        }
        return Result.error("权限不足");
    }

}
