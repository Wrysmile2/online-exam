package com.xyh.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.vo.ResultAPI;
import com.xyh.pojo.User;
import com.xyh.service.UserService;
import com.xyh.vo.request.admin.UserReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/user/")
@RestController("AdminUserController")
@Api(value = "管理员权限可操作", tags = "人员管理API")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("page/list")
    @ApiOperation("查询用户信息接口")
    public ResultAPI<IPage<User>> getPage(@RequestBody(required = false) UserReqVO vo, HttpServletRequest request){

        IPage<User> page = userService.findUserByCondition(vo);
        page.getRecords().forEach(item->{
            String path = item.getImagePath();
            item.setImagePath(request.getScheme()+"://"+ request.getServerName() +":"+request.getServerPort()+"/avatar/"+path);
        });
        return new ResultAPI<>(200,"查询成功",page);
    }

    @GetMapping("select/{id}")
    @ApiOperation("根据id查询单个用户信息接口")
    public ResultAPI<User> getByIds(@PathVariable Integer id,HttpServletRequest request){
        User user = userService.getById(id);
        user.setImagePath(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/avatar/"+user.getImagePath());
        return new ResultAPI<>(200,"查询成功", user);
    }

    @PostMapping("edit")
    @ApiOperation("新增或者删除人员信息接口")
    public ResultAPI<String> editUser(@RequestBody User user){
        boolean flag = userService.editUser(user);
        if(flag){
            return new ResultAPI<>(200,"操作成功","");
        }else{
            return new ResultAPI<>(0,"操作失败","");
        }
    }

    @PostMapping("updateStatus")
    @ApiOperation("对账号进行封禁还是启用调整")
    public ResultAPI<String> updateStatus(@RequestBody Integer[] ids){
            userService.updateStatus(ids);
        return new ResultAPI<>(200,"更新状态成功","");
    }

    @GetMapping("delete/{id}")
    @ApiOperation("根据编号逻辑删除用户")
    public ResultAPI<String> deleteById(@PathVariable Integer id){
        boolean flag = userService.deleteUser(id);
        if(flag){
            return new ResultAPI<>(200,"删除成功","");
        }
        return new ResultAPI<>(0,"删除失败","");
    }

    @GetMapping("selectUser/{username}")
    @ApiOperation("根据用户名查询用户")
    public ResultAPI<List<User>> getUserByUserName(@PathVariable String username){
        List<User> list = userService.getUserByUserName(username);

        return new ResultAPI<>(200,"查询成功",list);
    }

}
