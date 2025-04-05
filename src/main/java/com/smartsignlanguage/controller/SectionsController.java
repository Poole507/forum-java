package com.smartsignlanguage.controller;

import com.smartsignlanguage.domain.dto.AddSectionDto;
import com.smartsignlanguage.domain.vo.SectionManagerInfoVo;
import com.smartsignlanguage.domain.vo.SectionVo;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionsController {

    @Autowired
    private SectionsService sectionsService;
    //获取所有板块
    @GetMapping("/all")
    public Result getAllSections() {
        List<SectionVo> sectionVos = sectionsService.getAllSections();
        return Result.success(sectionVos);
    }
    /**
     * 获取板块的信息，板块名字，id，板块描述，总帖子数量，今日新帖，回复数量
     */
    @GetMapping("/info")
    public Result getSectionInfo() {
        List<SectionManagerInfoVo> sectionManagerInfoVos = sectionsService.getSectionInfo();
        return Result.success(sectionManagerInfoVos);
    }


    // 后台管理模块接口
    /**
     * 添加板块
     */
    @PostMapping("/add")
    public Result addSection(@RequestBody AddSectionDto addSectionDto) {
        return sectionsService.addSection(addSectionDto);
    }
    /**
     * 修改板块
     */
    @PostMapping("/edit")
    public Result editSection(@RequestBody AddSectionDto addSectionDto) {
        return sectionsService.edit(addSectionDto);
    }
    /**
     * 删除板块
     */
    @DeleteMapping("/delete")
    public Result deleteSection(Integer sectionId) {
        return sectionsService.delete(sectionId);
    }
}
