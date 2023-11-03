package com.xyh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.context.WebContext;
import com.xyh.base.vo.ResultAPI;
import com.xyh.config.cache.RedisCache;
import com.xyh.config.sysLog.SysLog;
import com.xyh.pojo.User;
import com.xyh.service.EmailService;
import com.xyh.service.UserService;
import com.xyh.vo.request.LoginUserReq;
import com.xyh.vo.request.RegisterUserReq;
import com.xyh.vo.request.student.ResetPassReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 在这个Controller内，都是不需要进行框架认证的
 */
@Slf4j
@RestController("LoginController")
@RequestMapping("/user/")
@Api(value = "登录接口相关",tags = "登录相关接口API")
public class LoginController {

    private final UserService userService;
    private final EmailService emailService;
    private final RedisCache redisCache;
    private final PasswordEncoder passwordEncoder;




    @Autowired
    public LoginController(UserService userService,EmailService emailService,RedisCache redisCache,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.redisCache = redisCache;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录账号")
    public ResultAPI<Map<String,String>> login(@RequestBody LoginUserReq req){
        Map<String, String> login = userService.login(req);
        return new ResultAPI<>(200,"登录成功",login);
    }

    /**
     * 登出
     * @return
     */
    @SysLog(content = "退出了Edu考试系统")
    @PostMapping("logout/{token}")
    @ApiOperation("登出")
    public ResultAPI<String> logout(@PathVariable String token){
        userService.logout(token);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"退出成功","");
    }

    @GetMapping("/info")
    @ApiOperation("获取个人信息,vue-admin自带的")
    public ResultAPI<User> getInfo(@RequestParam String token, HttpServletRequest  request){
        User user = userService.getInfo(token, request);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"查询成功",user);
    }

    /**
     * 注册
     * @return
     */
    @PostMapping("register")
    @ApiOperation("注册账号")
    public ResultAPI<String> register(@RequestBody RegisterUserReq req){
        String flag = userService.register(req);

        if(flag.equals("true")){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"注册成功","");
        }
        return new ResultAPI<>(ResponseConstant.FAILED,flag,"");
    }

    @GetMapping("validAccount/{account}")
    @ApiOperation("验证账号是否存在")
    public ResultAPI<Boolean> validAccount(@PathVariable String account){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount,account);
        User user = userService.getOne(wrapper);
        if(user != null){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"",true);
        }
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",false);
    }

    /**
     * 后期可以使用Redis来判断这个账户，在指定的时间请求了多少次验证码，超过次数不给发送
     * @param userEmail
     * @return
     */
    @GetMapping("sendCode/{userEmail}")
    @ApiOperation("发送验证码")
    public ResultAPI<String> sendCode(@PathVariable String userEmail){
        String code = emailService.sendCode(userEmail);
        redisCache.setCacheObject(userEmail,code,5, TimeUnit.MINUTES);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"发送成功","");
    }

    @PostMapping("resetPass")
    @ApiOperation("重置密码")
    public ResultAPI<String> resetPass(@RequestBody ResetPassReqVO vo){

        Boolean flag = userService.resetPass(vo);
        if(flag){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"密码重置成功，请注意查收邮箱","");
        }
        return new ResultAPI<>(ResponseConstant.FAILED,"验证失败，请重试","");
    }



}
