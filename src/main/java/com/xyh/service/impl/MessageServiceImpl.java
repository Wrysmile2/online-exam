package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.mapper.MessageUserMapper;
import com.xyh.mapper.UserMapper;
import com.xyh.pojo.Message;
import com.xyh.pojo.MessageUser;
import com.xyh.pojo.User;
import com.xyh.service.MessageService;
import com.xyh.mapper.MessageMapper;
import com.xyh.vo.request.admin.MessageReqVO;
import com.xyh.vo.request.admin.MessageSendReqVO;
import com.xyh.vo.request.other.PageVO;
import com.xyh.vo.response.admin.MessageRespVO;
import com.xyh.vo.response.student.StuMessageRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author xyh
* @description 针对表【tbl_message】的数据库操作Service实现
* @createDate 2022-12-27 22:26:14
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    private MessageUserMapper messageUserMapper;
    private UserMapper userMapper;

    @Autowired
    public MessageServiceImpl(MessageUserMapper messageUserMapper,UserMapper userMapper) {
        this.messageUserMapper = messageUserMapper;
        this.userMapper = userMapper;
    }

    @Override
    public IPage<MessageRespVO> getPageList(MessageReqVO vo) {
        System.out.println(vo.getPageIndex());
        IPage<MessageRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<MessageRespVO> pages = baseMapper.getByPage(page,vo);
        return pages;
    }

    @Override
    @Transactional
    public void sendMessage(MessageSendReqVO vo,Integer createUser) {
//        得到需要发送的用户列表
        List<Integer> userListId = sendUserList(vo);
//        先对公告入表
        Message message = new Message();
        BeanUtils.copyProperties(vo,message);
        message.setReceiveNum(userListId.size());
        message.setActualReceiveNum(0);
        message.setCreateUser(createUser);
        baseMapper.insert(message);
        Integer messageId = message.getId();
        //TODO 这一部分可以考虑使用线程池处理
//        将公告数据分别入message_user表
        userListId.stream().forEach(id ->{
            MessageUser messageUser = new MessageUser();
            messageUser.setReceiveId(id);
            messageUser.setReaded(false);
            messageUser.setCreateUser(createUser);
            messageUser.setMessageId(messageId);
            messageUserMapper.insert(messageUser);
        });
    }

    @Override
    @Transactional
    public void readMessage(Integer id) {
        //查看这条信息是否被阅读过
        MessageUser messageUser = messageUserMapper.selectById(id);
        if(messageUser.getReaded()) return;
        //更新阅读标志
       messageUser.setReaded(true);
       messageUser.setReadTime(new Date());
       messageUserMapper.updateById(messageUser);
       // 更新已读人数
       baseMapper.updateReadCount(messageUser.getMessageId());

    }

    @Override
    public IPage<StuMessageRespVO> selectStuList(PageVO vo, Integer userId) {
        IPage<StuMessageRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<StuMessageRespVO> stuList = baseMapper.selectStuList(page, userId);
        return stuList;
    }

    private List<Integer> sendUserList(MessageSendReqVO vo){
//        如果接收者id为空，那么即是全体公告
        LambdaQueryWrapper<User> userWrapper = null;
        if(vo.getReceiveUserIds().size() == 0){
            userWrapper = new LambdaQueryWrapper<>();
            userWrapper.ne(User::getRole,0)
                    .eq(User::getDeleted,false)
                    .ne(User::getRole,1);
            return userMapper.selectList(userWrapper).stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
        }
        return vo.getReceiveUserIds();
    }
}




