package com.smartsignlanguage.controller;

import com.smartsignlanguage.domain.dto.UpdataPasswordDto;
import com.smartsignlanguage.domain.dto.UserInfoDto;
import com.smartsignlanguage.domain.dto.UserLoginDto;
import com.smartsignlanguage.domain.dto.UserRegisterDto;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.domain.vo.ModeratorVo;
import com.smartsignlanguage.domain.vo.UserVo;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/all")
    public Result findAllUser(){
        List<UserVo> list = userService.findAllUser();
        return Result.success(list);
    }

    /**
     * 登入接口
     * @param userLoginDto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto)
    {
        return userService.login(userLoginDto);
    }

    /**
     * 注册接口
     * @param userRegisterDto
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto)
    {
        return userService.register(userRegisterDto);
    }

    /**
     * 上传头像
     * @param avatar
     * @return
     */
    @PostMapping("/avatar")
    public Result updateAvatar(MultipartFile avatar) {
        return userService.updateAvatar(avatar);
    }
    /**
     * 退出登入
     * @return
     */
    @PostMapping("/logout")
    public Result logout()
    {
        return userService.logout();
    }

    /**
     * 更新密码
     * @param updataPasswordDto
     * @return
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdataPasswordDto updataPasswordDto)
    {
        return userService.updatePassword(updataPasswordDto);
    }
    /**
     * 更新用户信息
     * @param userInfoDto
     * @return
     */
    @PostMapping("/updataUserInfo")
    public Result updateUserInfo(@RequestBody UserInfoDto userInfoDto)
    {
        return userService.updateUserInfo(userInfoDto);
    }

    /**
     * 获取所有版主信息
     */
    @GetMapping("/moderator")
    public Result getModeratorInfo()
    {
        List<ModeratorVo> moderatorInfo = userService.getModeratorInfo();
        return Result.success(moderatorInfo);
    }
    // 撤销版主某个板块的职位
    @PostMapping("/cancelModerator")
    public Result cancelModerator(Integer userId,Integer sectionId)
    {
        return userService.cancelModerator(userId,sectionId);
    }
    /**
     * 恢复版主某个板块的职位
     */
    @PostMapping("/recoverModerator")
    public Result recoverModerator(Integer userId,Integer sectionId)
    {
        return userService.recoverModerator(userId,sectionId);
    }
    /**
     * 添加版主
     */
    @PostMapping("/addModerator")
    public Result addModerator(Integer userId,Integer sectionId)
    {
        return userService.addModerator(userId,sectionId);
    }
    /**
     * 编辑用户的角色
     *
     */
    @PostMapping("/editRole")
    public Result editRole(Integer userId,String role)
    {
        return userService.editRole(userId,role) ;
    }
    /**
     * 获取申请人的申请信息
     */
    @GetMapping("/getApplications")
    public Result getApplications()
    {
        return userService.getApplications();
    }
    /**
     * 申请版主
     */
    @PostMapping("/applyModerator")
    public Result applyModerator(Integer sectionId,String reason)
    {
        return userService.applyModerator(sectionId,reason);
    }
    /**
     * 同意申请
     */
    @PostMapping("/agreeApply")
    public Result agreeApply(Integer userId,Integer sectionId)
    {
        return userService.agreeApply(userId,sectionId);
    }
    /**
     * 拒绝申请
     */
    @PostMapping("/refuseApply")
    public Result refuseApply(Integer userId,Integer sectionId)
    {
        return userService.refuseApply(userId,sectionId);
    }
}
