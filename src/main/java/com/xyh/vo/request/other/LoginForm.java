package com.xyh.vo.request.other;

import lombok.Data;

@Data
public class LoginForm {
    private String account;
    private String password;
    private Integer role;
}
