package com.smartsignlanguage.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private Integer id;

    private String username;

    private String email;

    private String token;

    private String avatar;

    //用户角色：普通用户、版主、管理员
    private String role;
}
