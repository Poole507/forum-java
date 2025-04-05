package com.smartsignlanguage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartsignlanguage.domain.dto.AddSectionDto;
import com.smartsignlanguage.domain.entity.Sections;
import com.smartsignlanguage.domain.vo.SectionManagerInfoVo;
import com.smartsignlanguage.domain.vo.SectionVo;
import com.smartsignlanguage.result.Result;

import java.util.List;


/**
 * 论坛版块表(Sections)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 01:26:02
 */
public interface SectionsService extends IService<Sections> {

    List<SectionVo> getAllSections();

    List<SectionManagerInfoVo> getSectionInfo();

    Result addSection(AddSectionDto addSectionDto);

    Result edit(AddSectionDto addSectionDto);

    Result delete(Integer sectionId);
}

