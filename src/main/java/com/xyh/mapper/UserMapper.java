package com.xyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.pojo.User;
import com.xyh.vo.response.other.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_user】的数据库操作Mapper
* @createDate 2022-12-27 22:18:13
* @Entity com.xyh.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE tbl_user SET deleted = 1 WHERE id = #{id}")
    int removeById(@Param("id") Integer id);

    List<KeyValue> getUserKeyValue();

    void updateUser(User user);

    @Select("SELECT COUNT(*) FROM tbl_user WHERE YEAR(create_time) = YEAR(NOW()) AND MONTH(create_time)= MONTH(NOW())\n")
    Integer getCurMonthRegisterCount();

}
