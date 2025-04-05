package com.smartsignlanguage.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * (Application)表实体类
 *
 * @author makejava
 * @since 2025-04-04 14:16:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("applications")
public class Applications  {
@TableId(type = IdType.AUTO)
    private Integer id;

//用户id
    private Integer userId;
//申请板块
    private Integer sectionId;
//申请理由
    private String reason;
//审批状态
    private int status;
    private Date time;


}

