package com.xyh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.config.sysLog.SysLog;
import com.xyh.pojo.Log;
import com.xyh.pojo.User;
import com.xyh.service.LogService;
import com.xyh.service.UserService;
import com.xyh.vo.request.SelfUpdateReq;
import com.xyh.vo.request.other.UpdatePassReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * UserController的通用处理
 */
@Slf4j
@RequestMapping("/common/user/")
@RestController("userController")
@Api(value = "个人信息管理",tags = "个人信息管理的API")
public class UserController {

    private final UserService userService;
    private final LogService logService;
    private final WebContext webContext;


    @Autowired
    public UserController(UserService userService, LogService logService,WebContext webContext) {
        this.userService = userService;
        this.logService = logService;
        this.webContext = webContext;
    }

    @PostMapping("upload")
    @ApiOperation("更新头像")
    public ResultAPI<String> uploadImage(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request){
        Integer userId = webContext.getCurrentUser().getId();
        String str = userService.updateAvator(file, userId, request);
        if(!"false".equals(str)){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"上传成功",str);
        }

        return new ResultAPI<>(ResponseConstant.FAILED,"上传失败","");
    }

    @GetMapping("current")
    @ApiOperation("得到当前用户信息")
    public ResultAPI<User> getCurrent(HttpServletRequest request){
        int userId = webContext.getCurrentUser().getId();
        return new ResultAPI<>(ResponseConstant.SUCCESS,"查询成功",userService.getCurrent(userId,request));
    }

    @PostMapping("updateSelf")
    @ApiOperation("更新个人信息")
    public ResultAPI<String> updateSelf(@RequestBody SelfUpdateReq req){
        int userId = webContext.getCurrentUser().getId();
        User user = new User();
        BeanUtils.copyProperties(req,user);
        user.setId(userId);
        userService.updateUser(user);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"更新成功","");
    }

    @SysLog(content = "更新个人密码")
    @PostMapping("updatePass")
    @ApiOperation("更新个人密码")
    public ResultAPI<String> updatePass(@RequestBody UpdatePassReqVO vo){
        int userId = webContext.getCurrentUser().getId();
        boolean flag = userService.updatePass(vo, userId);
        if(flag){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"更新成功","");
        }
        return new ResultAPI<>(ResponseConstant.FAILED,"请检查旧密码是否输入正确","");
    }

    @GetMapping("log")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @ApiOperation("查看个人日志")
    public ResultAPI<IPage<Log>> getUserLog(){
        Integer userId = webContext.getCurrentUser().getId();
        IPage<Log> log = logService.getUserLog(userId);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"成功",log);
    }


}
