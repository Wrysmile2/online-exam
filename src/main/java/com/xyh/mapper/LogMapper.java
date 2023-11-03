package com.xyh.mapper;

import com.xyh.pojo.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.response.other.KeyValue;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
* @author xyh
* @description 针对表【tbl_log】的数据库操作Mapper
* @createDate 2022-12-27 22:26:14
* @Entity com.xyh.pojo.Log
*/
@Mapper
public interface LogMapper extends BaseMapper<Log> {


//    List<KeyValue> getActiveData(@Param("minTime")String minTime , @Param("maxTime")String maxTime);
    List<KeyValue> getActiveData();

    @Select("SELECT COUNT(*) FROM tbl_log WHERE is_login = TRUE AND YEAR(create_time) = YEAR(NOW()) AND MONTH(create_time)= MONTH(NOW())\n")
    Integer getVisitCurMonthCount();

    List<KeyValue> getActiveDataByCondition(@Param("beginTime") String s, @Param("endTime") String s1, @Param("list") List<Integer> diffDays);
}




