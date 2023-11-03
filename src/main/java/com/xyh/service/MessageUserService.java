package com.xyh.service;

import com.xyh.pojo.MessageUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xyh
* @description 针对表【tbl_message_user】的数据库操作Service
* @createDate 2022-12-27 22:25:49
*/
public interface MessageUserService extends IService<MessageUser> {

    Integer selectUnreadCount(Integer userId);
}
