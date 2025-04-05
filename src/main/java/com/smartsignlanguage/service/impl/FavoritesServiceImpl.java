package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.entity.Favorites;
import com.smartsignlanguage.mapper.FavoritesMapper;
import com.smartsignlanguage.service.FavoritesService;
import org.springframework.stereotype.Service;

/**
 * 帖子收藏表(Favorites)表服务实现类
 *
 * @author makejava
 * @since 2025-04-03 00:15:17
 */
@Service("favoritesService")
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

    // 判断是否收藏
    @Override
    public boolean isFavorites(Integer topicId) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getTopicId, topicId);
        queryWrapper.eq(Favorites::getUserId, BaseContext.getCurrentId().intValue());
        Favorites favorites = getBaseMapper().selectOne(queryWrapper);
        if (favorites != null){
            return true;
        }
        return false;
    }
}
