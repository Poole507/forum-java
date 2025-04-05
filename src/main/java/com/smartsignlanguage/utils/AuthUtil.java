package com.smartsignlanguage.utils;

import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.mapper.UserMapper;
import com.smartsignlanguage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    private UserMapper userMapper;
    public String getRole(){
        // 获取用户的角色信息
        int userId = BaseContext.getCurrentId().intValue();
        User user = userMapper.selectById(userId);
        return user.getRole();
    }
}
