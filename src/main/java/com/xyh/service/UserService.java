package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.pojo.User;
import com.xyh.vo.request.LoginUserReq;
import com.xyh.vo.request.RegisterUserReq;
import com.xyh.vo.request.other.UpdatePassReqVO;
import com.xyh.vo.request.admin.UserReqVO;
import com.xyh.vo.request.student.ResetPassReqVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* @author 肖远华
* @description 针对表【tbl_user】的数据库操作Service
* @createDate 2022-12-27 22:18:13
*/
public interface UserService extends IService<User> {



    /**
     * 根据条件查询
     * @param vo
     */
    IPage<User> findUserByCondition(UserReqVO vo);

    /**
     * 编辑或者添加用户
     * @param user
     * @return
     */
    boolean editUser(User user);

    void updateStatus(Integer[] ids);


    Map<String,String> login(LoginUserReq user);

    boolean deleteUser(Integer id);

    List<User> getUserByUserName(String username);

    String updateAvator(MultipartFile file, Integer userId,  HttpServletRequest request);

    void updateUser(User user);

    boolean updatePass(UpdatePassReqVO vo,Integer userId);

    void logout(String token);

    Boolean resetPass(ResetPassReqVO vo);

    User getUserById(Integer id);

    String register(RegisterUserReq req);

    User getCurrent(Integer userId, HttpServletRequest request);

    User getInfo(String token, HttpServletRequest request);
}
