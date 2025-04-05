package com.smartsignlanguage.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户信息表(User)表实体类
 *
 * @author makejava
 * @since 2025-04-01 11:31:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    //用户ID，自增主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户名，唯一
    private String username;
    //密码，加密存储
    private String password;
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

