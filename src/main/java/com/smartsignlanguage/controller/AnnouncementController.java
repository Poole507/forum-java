package com.smartsignlanguage.controller;

import com.smartsignlanguage.domain.dto.AnnouncementDto;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.AnnouncementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementsService announcementsService;

    @GetMapping("/info")
    public Result getAnnouncementInfo() {
        return announcementsService.getAnnouncementInfo();
    }
    @PutMapping("/update")
    public Result updateAnnouncementInfo(@RequestBody AnnouncementDto announcementDto){
        return announcementsService.updateAnnouncementInfo(announcementDto);
    }
}
