package com.smartsignlanguage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartsignlanguage.domain.dto.AnnouncementDto;
import com.smartsignlanguage.domain.entity.Announcements;
import com.smartsignlanguage.result.Result;


/**
 * 论坛公告表(Announcements)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 22:16:54
 */
public interface AnnouncementsService extends IService<Announcements> {

    Result getAnnouncementInfo();

    Result updateAnnouncementInfo(AnnouncementDto announcementDto);
}

