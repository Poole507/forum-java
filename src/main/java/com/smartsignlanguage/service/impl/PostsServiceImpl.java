package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.domain.entity.Posts;
import com.smartsignlanguage.mapper.PostsMapper;
import com.smartsignlanguage.service.PostsService;
import org.springframework.stereotype.Service;

/**
 * 帖子回复表(Posts)表服务实现类
 *
 * @author makejava
 * @since 2025-04-02 23:51:35
 */
@Service("postsService")
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

}
