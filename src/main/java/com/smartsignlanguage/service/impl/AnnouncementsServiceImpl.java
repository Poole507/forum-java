package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.dto.AnnouncementDto;
import com.smartsignlanguage.domain.entity.Announcements;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.mapper.AnnouncementsMapper;
import com.smartsignlanguage.mapper.UserMapper;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.AnnouncementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 论坛公告表(Announcements)表服务实现类
 *
 * @author makejava
 * @since 2025-04-02 22:16:54
 */
@Service("announcementsService")
public class AnnouncementsServiceImpl extends ServiceImpl<AnnouncementsMapper, Announcements> implements AnnouncementsService {

    @Autowired
    UserMapper userMapper;
    @Override
    public Result getAnnouncementInfo() {
        // 获取公告信息
        List<Announcements> announcements = getBaseMapper().selectList(null);
        if (announcements != null) {
            return Result.success(announcements.get(0));
        }
        return null;
    }

    @Override
    public Result updateAnnouncementInfo(AnnouncementDto announcementDto) {
        // 检查该用户是否有权限
        User user = userMapper.selectById(BaseContext.getCurrentId().intValue());
        if("admin".equals(user.getRole())){
            Announcements announcements = getBaseMapper().selectById(1);
            announcements.setContent(announcementDto.getContent());
            announcements.setTitle(announcementDto.getTitle());
            getBaseMapper().updateById(announcements);
            return Result.success("更新公告成功！");
        }else{
            return Result.error("权限不足！");
        }
    }
}
