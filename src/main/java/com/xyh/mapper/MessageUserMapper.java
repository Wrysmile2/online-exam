package com.xyh.mapper;

import com.xyh.pojo.MessageUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
* @author xyh
* @description 针对表【tbl_message_user】的数据库操作Mapper
* @createDate 2022-12-27 22:25:49
* @Entity com.xyh.pojo.MessageUser
*/
@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUser> {

}




