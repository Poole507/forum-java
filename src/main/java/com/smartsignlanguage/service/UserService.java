package com.smartsignlanguage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartsignlanguage.domain.dto.UpdataPasswordDto;
import com.smartsignlanguage.domain.dto.UserInfoDto;
import com.smartsignlanguage.domain.dto.UserLoginDto;
import com.smartsignlanguage.domain.dto.UserRegisterDto;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.domain.vo.ModeratorVo;
import com.smartsignlanguage.domain.vo.UserVo;
import com.smartsignlanguage.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2025-01-20 17:17:11
 */

public interface UserService extends IService<User> {

    List<UserVo> findAllUser();

    Result login(UserLoginDto userLoginDto);

    Result register(UserRegisterDto userRegisterDto);

    Result updateAvatar(MultipartFile avatar);

    Result logout();

    Result updatePassword(UpdataPasswordDto updataPasswordDto);

    Result updateUserInfo(UserInfoDto userInfoDto);

    List<ModeratorVo> getModeratorInfo();

    Result cancelModerator(Integer userId, Integer sectionId);

    Result recoverModerator(Integer userId, Integer sectionId);

    Result editRole(Integer userId, String role);

    Result addModerator(Integer userId, Integer sectionId);

    Result getApplications();

    Result applyModerator(Integer sectionId, String reason);

    Result agreeApply(Integer userId, Integer sectionId);

    Result refuseApply(Integer userId, Integer sectionId);
}

