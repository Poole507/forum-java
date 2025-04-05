package com.smartsignlanguage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartsignlanguage.domain.entity.Favorites;


/**
 * 帖子收藏表(Favorites)表服务接口
 *
 * @author makejava
 * @since 2025-04-03 00:15:17
 */
public interface FavoritesService extends IService<Favorites> {

    boolean isFavorites(Integer topicId);
}

