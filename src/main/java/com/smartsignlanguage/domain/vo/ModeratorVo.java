package com.smartsignlanguage.domain.vo;

import com.smartsignlanguage.domain.entity.Sections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ModeratorVo {
    //用户ID，自增主键
    private Integer id;
    //用户名，唯一
    private String username;
    //用户角色：普通用户、版主、管理员
    private String role;
    // 管理板块名字、
    private List<SectionVo> sectionVos;
    //用户状态：1-正常，0-禁用
    private Integer status;
}
