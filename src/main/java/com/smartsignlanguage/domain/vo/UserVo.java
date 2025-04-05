package com.smartsignlanguage.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVo {
    //用户ID，自增主键
    private Integer id;
    //用户名，唯一
    private String username;
    //邮箱，唯一
    private String email;
    //头像URL
    private String avatar;
    //用户角色：普通用户、版主、管理员
    private String role;
    //注册时间
    private Date createdAt;
    //用户状态：1-正常，0-禁用
    private Integer status;
}
