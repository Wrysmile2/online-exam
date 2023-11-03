package com.xyh.utils;


import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    private  static Random random = new Random();

    public static String  getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 验证码的生成
     * @return
     */
    public static String generatorValidationCode(){
        Integer code = new Random().nextInt(999999);
        if(code< 100000){
            return String.valueOf(code + 100000);
        }
        return String.valueOf(code);
    }


    public static String resetPass(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(String.valueOf(random.nextInt(10)));
        }
        list.add(getUUID().substring(0,6));
        return list.stream().collect(Collectors.joining(""));
    }
}
