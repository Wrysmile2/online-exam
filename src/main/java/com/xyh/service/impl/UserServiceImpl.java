package com.xyh.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.base.constants.CommonConstant;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.config.mvc.security.vo.LoginUser;
import com.xyh.config.sysLog.UserEvent;
import com.xyh.exceptions.CustomException;
import com.xyh.pojo.Log;
import com.xyh.pojo.User;
import com.xyh.service.EmailService;
import com.xyh.service.UserService;
import com.xyh.mapper.UserMapper;
import com.xyh.utils.JwtUtil;
import com.xyh.utils.Utils;
import com.xyh.vo.request.LoginUserReq;
import com.xyh.vo.request.RegisterUserReq;
import com.xyh.vo.request.other.UpdatePassReqVO;
import com.xyh.vo.request.admin.UserReqVO;
import com.xyh.vo.request.student.ResetPassReqVO;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author xyh
* @description 针对表【tbl_user】的数据库操作Service实现
* @createDate 2022-12-27 22:18:13
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

    private final AuthenticationManager authenticationManager;
    private final RedisCache redisCache;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final FileUpLoadServiceImpl fileUpLoadService;

    private final ApplicationEventPublisher publisher;

    @Value("${img.basePath}")
    private String basePath;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, RedisCache redisCache,PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher publisher,EmailService emailService, FileUpLoadServiceImpl fileUpLoadService) {
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.emailService = emailService;
        this.fileUpLoadService = fileUpLoadService;
    }

    @Override
    public IPage<User> findUserByCondition(UserReqVO vo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!Strings.isEmpty(vo.getUsername()),User::getUsername,vo.getUsername())
                .eq(User::getRole,vo.getRole())
                .eq(vo.getStatus() != null,User::getUserStatus,vo.getStatus())
                .eq(User::getDeleted,0)
                .orderByDesc(User::getCreateTime);
        IPage<User> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<User> pages = baseMapper.selectPage(page, wrapper);

        if(pages.getPages() < vo.getPageIndex()){
            vo.setPageIndex(1L);
            page = new Page<>(vo.getPageIndex(),vo.getPageSize());
            pages = baseMapper.selectPage(page,wrapper);
        }

        return pages;
    }

    @Override
    public boolean editUser(User user) {
        int count = 0;
        String secretPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(secretPass);
        if(user.getId() == null){
            user.setImagePath("default.jpg");
            count = baseMapper.insert(user);
        }else{
            count = baseMapper.updateById(user);
            //清除缓存
//            redisCache.deleteObject("user:"+user.getId());
            redisCache.delCacheMapValue(RedisConstant.USER,String.valueOf(user.getId()));
        }
        return count > 0;
    }

    @Override
    public void updateStatus(Integer[] ids) {
        List<User> list = baseMapper.selectBatchIds(Arrays.asList(ids));
        List<String> fields = new ArrayList<>();
        list = list.stream().map(item ->{
            item.setUserStatus(item.getUserStatus() == 0 ? 1 : 0);
            fields.add(String.valueOf(item.getId()));
            return item;
        }).collect(Collectors.toList());
        list.stream().forEach(item -> baseMapper.updateById(item));
        // 批量清理缓存
        redisCache.batchDelCacheMapValue(RedisConstant.USER, fields);
    }

    @Override
    public Map<String,String> login(LoginUserReq user) {
        //进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(),user.getPassword());
        // UserDetailsService中loadUserByUsername会调用
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        //判断登录端
        User one = loginUser.getUser();
        if(!one.getRole().equals(user.getRole())){
            throw new CustomException("权限不足");
        }
        //验证成功，那么就将user保存到redis中,生成token
        String account = one.getAccount();
        String token = JwtUtil.createJWT(account);
        Map<String,String> map = new HashMap<>();
        map.put(CommonConstant.TOKEN,token);
        // 存储token
        redisCache.setCacheObject(RedisConstant.LOGIN_PREFIX+account,loginUser,180, TimeUnit.MINUTES);
        //发布日志
        Log log = new Log(one.getUsername()+"登录了Edu考试系统",one.getUsername(),one.getId(),true,new Date());
        publisher.publishEvent(new UserEvent(log));
        return map;
    }


    @Override
    public boolean deleteUser(Integer id) {
        boolean flag = baseMapper.removeById(id) > 0;
        //清理缓存
//        redisCache.deleteObject("user:"+id);
        redisCache.delCacheMapValue(RedisConstant.USER,String.valueOf(id));
        return flag;
    }


    @Override
    public List<User> getUserByUserName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(User::getRole,2)
                .eq(User::getDeleted,false)
                .like(Strings.isNotEmpty(username),User::getUsername,username);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public String updateAvator(MultipartFile file, Integer userId, HttpServletRequest request) {
        try {
            String path = fileUpLoadService.uploadFile(file);
            String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/avatar/"+path;
            User user = baseMapper.selectById(userId);
            String imagePath = user.getImagePath() == null ? "" : user.getImagePath();
            imagePath = basePath+imagePath;
            File f = new File(imagePath);
            if(f.exists()){
                f.delete();
            }
            user.setImagePath(path);
            baseMapper.updateById(user);
            System.out.println(user);
            // 更新缓存
            Object obj = redisCache.getCacheMapValue(RedisConstant.USER, String.valueOf(userId));
            if(!Objects.isNull(obj)){
                User redisUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
                redisUser.setImagePath(url);
                redisCache.setCacheMapValue(RedisConstant.USER,String.valueOf(userId),redisUser);
            }
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";

    }

    /**
     * 更新个人信息
     * @param user
     */
    @Override
    public void updateUser(User user) {

        baseMapper.updateUser(user);
        redisCache.delCacheMapValue(RedisConstant.USER,String.valueOf(user.getId()));
//        String redisKey = RedisConstant.LOGIN_PREFIX+user.getAccount();

//        Object obj = redisCache.getCacheObject(redisKey);
//        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(obj),LoginUser.class);
//        loginUser.setUser(user);
//        redisCache.setCacheObject(redisKey,loginUser,180,TimeUnit.MINUTES);
    }

    /**
     *
     * @param vo
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public boolean updatePass(UpdatePassReqVO vo,Integer userId) {
        User user = baseMapper.selectById(userId);
        if(!passwordEncoder.matches(vo.getOldPass(),user.getPassword())){
            return false;
        }
        String pass = passwordEncoder.encode(vo.getNewPass());
        user.setPassword(pass);
        boolean flag = baseMapper.updateById(user) > 0;
        //进行redis的数据更新
        Object obj = redisCache.getCacheMapValue(RedisConstant.USER, String.valueOf(userId));
        User redisUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
        redisUser.setPassword(pass);
//        String redisKey = RedisConstant.LOGIN_PREFIX+user.getAccount();
//        Object obj = redisCache.getCacheObject(redisKey);
//        LoginUser login = JSON.parseObject(JSON.toJSONString(obj),LoginUser.class);
//        login.getUser().setPassword(pass);
//        redisCache.setCacheObject(redisKey,login,180,TimeUnit.MINUTES);
        return flag;
    }

    @Override
    public void logout(String token) {
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String account = claims.getSubject();
        String redisKey = RedisConstant.LOGIN_PREFIX+account;
        boolean flag = redisCache.deleteObject(redisKey);
    }

    @Override
    public Boolean resetPass(ResetPassReqVO vo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount,vo.getAccount())
                .eq(User::getUserEmail,vo.getUserEmail())
                .eq(User::getRole,2);
        User user = baseMapper.selectOne(wrapper);
        if(Objects.isNull(user)){
            return false;
        }
        // 验证码过期
        String code = redisCache.getCacheObject(user.getUserEmail());
        if(Strings.isEmpty(code)){
            return false;
        }
        // 验证成功
        if(code.equals(vo.getVailCode())){
            String pass = Utils.resetPass();
            String secretPass = passwordEncoder.encode(pass);
            user.setPassword(secretPass);
            baseMapper.updateById(user);
            // 异步发送信息，避免响应速度慢
            new Thread(()->{
                emailService.sendRestPass(vo.getUserEmail(), pass);
            }).start();
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Integer id) {
        User user = null;
//        Object obj = redisCache.getCacheObject("user:" + id);
        Object obj = redisCache.getCacheMapValue(RedisConstant.USER ,String.valueOf(id));
        if(!Objects.isNull(obj)){
            user = (User) obj;
        }else{
            user = baseMapper.selectById(id);
            redisCache.setCacheMapValue(RedisConstant.USER,String.valueOf(id),user);
        }
        return user;
    }

    @Override
    @Transactional
    public String register(RegisterUserReq req) {
        // 后端检测账号是否已经被注册
        if(!Objects.isNull(getOne(new LambdaQueryWrapper<User>().eq(User::getAccount,req.getAccount())))){
            return "账号已被注册，请另换";
        }
        User registerUser = new User();
        BeanUtils.copyProperties(req,registerUser);
        registerUser.setPassword(passwordEncoder.encode(req.getPassword()));
        registerUser.setImagePath("default.jpg");
        return  save(registerUser) ? "true" : "注册失败，请稍后重试";
    }

    @Override
    public User getCurrent(Integer userId, HttpServletRequest request) {
        Object obj = redisCache.getCacheMapValue(RedisConstant.USER, String.valueOf(userId));
        User user = null;
        if(Objects.isNull(obj)){
            user = baseMapper.selectById(userId);
            user.setImagePath(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/avatar/"+user.getImagePath());
            redisCache.setCacheMapValue(RedisConstant.USER,String.valueOf(userId),user);
        }else{
            user = JSON.parseObject(JSON.toJSONString(obj), User.class);
        }
        return user;
    }

    @Override
    public User getInfo(String token, HttpServletRequest request) {
        String account = "";
        String key = "";
        try {
            Claims claims = JwtUtil.parseJWT(token);
            account = claims.getSubject();
            key = RedisConstant.LOGIN_PREFIX+account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject(key)), LoginUser.class);
        Integer userId = loginUser.getUser().getId();
        Object obj = redisCache.getCacheMapValue(RedisConstant.USER, String.valueOf(userId));
        User user = JSON.parseObject(JSON.toJSONString(obj),User.class);
        if(Objects.isNull(obj)){
            user = baseMapper.selectById(userId);
            user.setImagePath(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/avatar/"+user.getImagePath());
            redisCache.setCacheMapValue(RedisConstant.USER,String.valueOf(userId),user);
        }
        return user;
    }


}
