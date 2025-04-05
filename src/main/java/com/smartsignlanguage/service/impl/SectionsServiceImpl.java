package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.dto.AddSectionDto;
import com.smartsignlanguage.domain.entity.Sections;
import com.smartsignlanguage.domain.entity.Topics;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.domain.vo.SectionManagerInfoVo;
import com.smartsignlanguage.domain.vo.SectionVo;
import com.smartsignlanguage.mapper.SectionsMapper;
import com.smartsignlanguage.mapper.TopicsMapper;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.TopicsService;
import com.smartsignlanguage.service.UserService;
import com.smartsignlanguage.utils.AuthUtil;
import com.smartsignlanguage.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartsignlanguage.service.SectionsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 论坛版块表(Sections)表服务实现类
 *
 * @author makejava
 * @since 2025-04-02 01:26:02
 */
@Service("sectionsService")
public class SectionsServiceImpl extends ServiceImpl<SectionsMapper, Sections> implements SectionsService {

    @Autowired
    private UserService userService;
    @Autowired
    private TopicsMapper topicsMapper;
    @Autowired
    private AuthUtil  authUtil;

    @Override
    public List<SectionVo> getAllSections() {
        // 获取所有板块的信息
        List<Sections> sections = getBaseMapper().selectList(null);
        // 转化为SectionVo列表
        List<SectionVo> sectionVos = BeanCopyUtils.copyBeanList(sections, SectionVo.class);
        return sectionVos;
    }

    // 获取版主管理板块的信息
    @Override
    public List<SectionManagerInfoVo> getSectionInfo() {
        int userId = BaseContext.getCurrentId().intValue();
        // 判断该用户是否为版主
        User user = userService.getById(userId);
        String role = user.getRole();
        if ("admin".equals(role) || "moderator".equals(role)) {
            LambdaQueryWrapper<Sections> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Sections::getModeratorId, userId);
            List<Sections> sections = getBaseMapper().selectList(queryWrapper);
            // 转化为SectionManagerInfoVo列表
            List<SectionManagerInfoVo> sectionManagerInfoVos = BeanCopyUtils.copyBeanList(sections, SectionManagerInfoVo.class);

            // 需要统计今日新帖数量
            for (SectionManagerInfoVo sectionManagerInfoVo : sectionManagerInfoVos) {
                // 遍历每一个版块，统计今日新帖数量
                Integer sectionId = sectionManagerInfoVo.getId();

                // 获取今天零点时间
                LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

                LambdaQueryWrapper<Topics> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Topics::getSectionId, sectionId)
                        .ge(Topics::getCreatedAt, todayStart);

                Integer newTopicNum = topicsMapper.selectCount(queryWrapper1);
                sectionManagerInfoVo.setTodayTopicCount(newTopicNum);
            }
            return sectionManagerInfoVos;
        } else {
            return null;
        }
    }

    @Override
    public Result addSection(AddSectionDto addSectionDto) {
        int userId = BaseContext.getCurrentId().intValue();
        String role = userService.getById(userId).getRole();
        if (("admin".equals(role) ) && addSectionDto != null) {
            Sections sections = BeanCopyUtils.copyBean(addSectionDto, Sections.class);
            sections.setCommentCount(0);
            sections.setTopicCount(0);
            sections.setCreatedAt(new Date());
            String iconUrl = sections.getIconUrl();
            if(iconUrl == null){
                sections.setIconUrl("fas fa-graduation-cap");
            }
            sections.setStatus(1);
            getBaseMapper().insert(sections);
            return Result.success("添加成功");

        }
        return Result.error("服务器异常");
    }

    // 修改板块信息
    @Override
    public Result edit(AddSectionDto addSectionDto) {
        String role = authUtil.getRole();
        if (("admin".equals(role) || "moderator".equals(role)) && addSectionDto != null) {
            Sections sections = BeanCopyUtils.copyBean(addSectionDto, Sections.class);
            // 根据板块id更新数据
            sections.setId(addSectionDto.getSectionId());
            getBaseMapper().updateById(sections);
            return Result.success("修改成功");
        }
        return null;
    }

    @Override
    public Result delete(Integer sectionId) {
        String role = authUtil.getRole();
        if (("admin".equals(role))) {
            removeById(sectionId);
            return Result.success("删除成功");
        }
        return Result.error("权限不足");
    }
}
