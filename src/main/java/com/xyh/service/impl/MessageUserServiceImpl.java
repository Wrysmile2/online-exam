package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.pojo.Message;
import com.xyh.pojo.MessageUser;
import com.xyh.service.MessageUserService;
import com.xyh.mapper.MessageUserMapper;
import org.springframework.stereotype.Service;

/**
* @author xyh
* @description 针对表【tbl_message_user】的数据库操作Service实现
* @createDate 2022-12-27 22:25:49
*/
@Service
public class MessageUserServiceImpl extends ServiceImpl<MessageUserMapper, MessageUser>
    implements MessageUserService{


    @Override
    public Integer selectUnreadCount(Integer userId) {
        LambdaQueryWrapper<MessageUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageUser::getReaded,false)
                .eq(MessageUser::getReceiveId,userId);
        return baseMapper.selectCount(wrapper);
    }

}




