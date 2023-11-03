package com.xyh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 月份的帮助类
 */
public class DateUtils {

    private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

    private static final  SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static  String getCurrentMinTime(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int minDay = 1;
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,minDay);
        calendar.set(Calendar.HOUR_OF_DAY,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);
        System.out.println(year+""+month+""+minDay);
        return format.format(calendar.getTime());
    }

    public static  String getCurrentMaxTime(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,maxDay);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        System.out.println(year+""+month+""+maxDay);
        return format.format(calendar.getTime());
    }

    /**
     * 得到当前月份的天数
     * @return
     */
    public static List<String> getCurrentMonthDays(){
        Calendar calendar = Calendar.getInstance();
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= maximum; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    /**
     * 得到考试的时长
     * @param beginTime
     * @param endTime
     * @return
     */
    public static Integer getExamTime(Date beginTime,Date endTime){
        Long times = endTime.getTime() - beginTime.getTime();
        return Math.toIntExact(times / 1000 / 60);
    }

    public static Integer getFinishedTag(Date beginTime,Date endTime){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        if(now.getTime() > endTime.getTime()){
            return 2;
        }
        if(now.getTime() < beginTime.getTime()){
            return 0;
        }
        return  1;
    }

    public static List<Integer> getDiffDays(String beginTime,String endTime){
        List<Integer> list = new ArrayList<>();

        Date begin = null;
        Date end = null;
        try {
            begin = simpleFormat.parse(beginTime);
            end = simpleFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long bTimes = begin.getTime();
        long eTimes = end.getTime();

        long days = (eTimes - bTimes) / 1000 / 60 / 60 / 24 + 1;
        for (int i = 0; i < days; i++) {
            list.add(1);
        }

        return list;
    }

}
