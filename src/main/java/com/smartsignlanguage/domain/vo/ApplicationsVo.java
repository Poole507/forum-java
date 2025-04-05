package com.smartsignlanguage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationsVo {
    private Integer id;
    //用户id
    private Integer userId;
    //用户头像
    private String avatarUrl;
    //用户名
    private String username;
    //申请板块
    private Integer sectionId;
    // 板块名字
    private String sectionName;
    //申请理由
    private String reason;
    //审批状态
    private int status;
    // 申请时间
    private Date time;
}
