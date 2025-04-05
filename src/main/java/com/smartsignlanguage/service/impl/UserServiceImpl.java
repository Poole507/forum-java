package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.constant.JwtClaimsConstant;
import com.smartsignlanguage.context.BaseContext;
import com.smartsignlanguage.domain.dto.UpdataPasswordDto;
import com.smartsignlanguage.domain.dto.UserInfoDto;
import com.smartsignlanguage.domain.dto.UserLoginDto;
import com.smartsignlanguage.domain.dto.UserRegisterDto;
import com.smartsignlanguage.domain.entity.Applications;
import com.smartsignlanguage.domain.entity.Sections;
import com.smartsignlanguage.domain.entity.User;
import com.smartsignlanguage.domain.vo.*;
import com.smartsignlanguage.exception.PasswordErrorException;
import com.smartsignlanguage.exception.UserIsNotExistException;
import com.smartsignlanguage.mapper.ApplicationMapper;
import com.smartsignlanguage.mapper.SectionsMapper;
import com.smartsignlanguage.mapper.UserMapper;
import com.smartsignlanguage.properties.JwtProperties;
import com.smartsignlanguage.result.Result;
import com.smartsignlanguage.service.UserService;
import com.smartsignlanguage.utils.AuthUtil;
import com.smartsignlanguage.utils.BeanCopyUtils;
import com.smartsignlanguage.utils.JwtUtil;
import com.smartsignlanguage.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2025-01-20 17:25:17
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private SectionsMapper sectionsMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVo> findAllUser() {
        // 验证是否有权限
        String role = authUtil.getRole();
        if ("admin".equals(role)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            List<User> list = getBaseMapper().selectList(lambdaQueryWrapper);
            // 转化
            List<UserVo> userVos = BeanCopyUtils.copyBeanList(list, UserVo.class);
            return userVos;
        }
        return null;
    }

    @Override
    public Result<UserLoginVo> login(UserLoginDto userLoginDto) {
        UserMapper userMapper = getBaseMapper();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userLoginDto.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new UserIsNotExistException("用户不存在！");
        }
        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new PasswordErrorException("密码错误");
        }
        //判断完成后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );
        UserLoginVo userLoginVo = BeanCopyUtils.copyBean(user, UserLoginVo.class);
        userLoginVo.setToken(token);
        return Result.success(userLoginVo);
    }

    @Override
    public Result register(UserRegisterDto userRegisterDto) {
        // 查看数据库有没有该用户
        UserMapper userMapper = getBaseMapper();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userRegisterDto.getUsername());
        if (userMapper.selectOne(wrapper) != null) {
            return Result.error("用户名已存在");
        }
        // 添加用户默认头像
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);
        user.setAvatar("https://shuwu-java.oss-cn-chengdu.aliyuncs.com/avatar/1739195886968_%E5%A4%B4%E5%83%8F%20%2811%29.png");
        user.setCreatedAt(new Date());
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
        return Result.success("注册成功！");
    }

    @Override
    public Result updateAvatar(MultipartFile file) {
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 构建对象名称，可以使用时间戳避免重名
            String objectName = "avatar2/" + System.currentTimeMillis() + "_" + originalFilename;

            // 上传文件到阿里云OSS
            String url = aliOssUtil.upload(file.getBytes(), objectName);

            // 获取当前登录用户ID
            Long userId = BaseContext.getCurrentId();


            // 更新用户头像
            User user = new User();
            user.setId(userId.intValue());
            user.setAvatar(url);

            // 更新数据库
            updateById(user);

            return Result.success(url);
        } catch (IOException e) {
            log.error("头像上传失败：{}", e.getMessage());
            return Result.error("头像上传失败");
        }
    }

    @Override
    public Result logout() {
        BaseContext.removeCurrentId();
        log.info("退出成功！");
        return Result.success("退出成功！");
    }

    @Override
    public Result updatePassword(UpdataPasswordDto updataPasswordDto) {
        // 获取用户id和旧密码和新密码
        String oldPassword = updataPasswordDto.getOld_password();
        String newPassword = updataPasswordDto.getNew_password();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, BaseContext.getCurrentId());
        userLambdaQueryWrapper.eq(User::getPassword, oldPassword);
        User user = getBaseMapper().selectOne(userLambdaQueryWrapper);
        if (user != null) {
            user.setPassword(newPassword);
            updateById(user);
            return Result.success("修改成功！");
        }
        return Result.error("密码错误！");
    }

    @Override
    public Result updateUserInfo(UserInfoDto userInfoDto) {
        User user = BeanCopyUtils.copyBean(userInfoDto, User.class);
        // 更新用户信息
        int userId = BaseContext.getCurrentId().intValue();
        user.setId(userId);
        getBaseMapper().updateById(user);
        return Result.success("更新成功");
    }

    @Override
    public List<ModeratorVo> getModeratorInfo() {
        // 获取所有版主的信息
        String role = authUtil.getRole();
        if ("admin".equals(role)) {
            List<User> moderator = getBaseMapper().selectList(new LambdaQueryWrapper<User>().eq(User::getRole, "moderator"));
            if (moderator != null) {
                List<ModeratorVo> moderatorVos = BeanCopyUtils.copyBeanList(moderator, ModeratorVo.class);
                for (ModeratorVo moderatorVo : moderatorVos) {
                    // 获取负责的板块信息
                    LambdaQueryWrapper<Sections> sectionsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    sectionsLambdaQueryWrapper.eq(Sections::getModeratorId, moderatorVo.getId());
                    List<Sections> sections = sectionsMapper.selectList(sectionsLambdaQueryWrapper);
                    List<SectionVo> sectionVos = BeanCopyUtils.copyBeanList(sections, SectionVo.class);
                    moderatorVo.setSectionVos(sectionVos);
                }
                return moderatorVos;
            }
        }
        return null;
    }

    // 撤销职位
    @Override
    public Result cancelModerator(Integer userId, Integer sectionId) {
        if ("admin".equals(authUtil.getRole())) {
            // 更新对应板块的状态
            Sections sections = new Sections();
            sections.setId(sectionId);
            sections.setStatus(0);
            sectionsMapper.updateById(sections);
            // 如果该用户没有其他板块，则将用户角色改为普通用户
            LambdaQueryWrapper<Sections> sectionsLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            sectionsLambdaQueryWrapper1.eq(Sections::getModeratorId, userId);
            sectionsLambdaQueryWrapper1.eq(Sections::getStatus, 1);
            if (sectionsMapper.selectList(sectionsLambdaQueryWrapper1).isEmpty()) {
                User user = new User();
                user.setId(userId);
                user.setRole("user");
                getBaseMapper().updateById(user);
            }
            return Result.success("撤销成功！");
        }
        return null;
    }
    // 恢复职务
    @Override
    public Result recoverModerator(Integer userId, Integer sectionId) {
        if ("admin".equals(authUtil.getRole())) {
            // 更新对应板块的状态
            Sections sections = new Sections();
            sections.setId(sectionId);
            sections.setStatus(1);
            sectionsMapper.updateById(sections);
            // 如果该用户为用户，则将用户角色改为版主
            if("user".equals(getBaseMapper().selectById(userId).getRole())){
                User user = new User();
                user.setId(userId);
                user.setRole("moderator");
                getBaseMapper().updateById(user);
            }
            return Result.success("恢复成功！");
        }
        return null;
    }

    // 修改用户角色
    @Override
    public Result editRole(Integer userId, String role) {
        if ("admin".equals(authUtil.getRole())) {
            User user = new User();
            user.setId(userId);
            user.setRole(role);
            getBaseMapper().updateById(user);
            return Result.success("修改成功！");
        }
        return Result.error("权限不足！");
    }

    @Override
    public Result addModerator(Integer userId, Integer sectionId) {
        // 添加对应板块的版主
        if ("admin".equals(authUtil.getRole())) {
            Sections sections = new Sections();
            sections.setId(sectionId);
            sections.setModeratorId(userId);
        }
        return null;
    }

    @Override
    public Result getApplications() {
        // 获取所有申请信息
        if ("admin".equals(authUtil.getRole())) {
            List<Applications> applications = applicationMapper.selectList(null);
            if (applications != null) {
                List<ApplicationsVo> applicationVos = BeanCopyUtils.copyBeanList(applications, ApplicationsVo.class);
                // 查询该用户的用户名，头像
                for (ApplicationsVo applicationVo : applicationVos) {
                    User user = userMapper.selectById(applicationVo.getUserId());
                    if (user != null) {
                        applicationVo.setAvatarUrl(user.getAvatar());
                        applicationVo.setUsername(user.getUsername());
                    }
                    // 查询对应板块的名字
                    Sections sections = sectionsMapper.selectById(applicationVo.getSectionId());
                    if (sections != null) {
                        applicationVo.setSectionName(sections.getName());
                    }
                }
                return Result.success(applicationVos);
            }
        }
        return null;
    }

    // 版主申请
    @Override
    public Result applyModerator(Integer sectionId, String reason) {

            Applications applications = new Applications();
            applications.setUserId(BaseContext.getCurrentId().intValue());
            applications.setSectionId(sectionId);
            applications.setReason(reason);
            applications.setStatus(0);
            applications.setTime(new Date());
            if (applicationMapper.insert(applications) > 0) {
                return Result.success("申请成功！");
            }
        return null;
    }

    // 同意申请
    @Override
    public Result agreeApply(Integer userId, Integer sectionId) {
        String role = authUtil.getRole();
        if ("admin".equals(role)) {
            Sections sections = new Sections();
            sections.setId(sectionId);
            sections.setModeratorId(userId);
            if (sectionsMapper.updateById(sections) > 0) {
                // 删除申请信息
                LambdaQueryWrapper<Applications> applicationsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                applicationsLambdaQueryWrapper.eq(Applications::getUserId, userId);
                applicationsLambdaQueryWrapper.eq(Applications::getSectionId, sectionId);
                applicationMapper.delete(applicationsLambdaQueryWrapper);
                // 更新用户身份
                User user = new User();
                user.setId(userId);
                user.setRole("moderator");
                getBaseMapper().updateById(user);
                return Result.success("同意申请成功！");
            }
        }
        return Result.error("权限不足！");
    }

    @Override
    public Result refuseApply(Integer userId, Integer sectionId) {
        // 删除申请信息
        LambdaQueryWrapper<Applications> applicationsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        applicationsLambdaQueryWrapper.eq(Applications::getUserId, userId);
        applicationsLambdaQueryWrapper.eq(Applications::getSectionId, sectionId);
        applicationMapper.delete(applicationsLambdaQueryWrapper);
        return Result.success("执行拒绝成功");
    }
}
