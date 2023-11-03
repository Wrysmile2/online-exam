package com.xyh.base.enums;

import com.xyh.base.constants.RoleConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色权限的枚举
 */
public enum RoleEnum {

    USER(2, RoleConstant.USER),
    TEACHER(1,RoleConstant.TEACHER),
    ADMIN(0,RoleConstant.ADMIN);

    private final static Map<Integer,RoleEnum> keyMap = new HashMap<>();

    static {
        for (RoleEnum item : RoleEnum.values()){
            keyMap.put(item.getCode(),item);
        }
    }

    public static RoleEnum fromCode(Integer code){
        return keyMap.get(code);
    }

    int code;
    String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName(){
        return "ROLE_"+name;
    }
}
