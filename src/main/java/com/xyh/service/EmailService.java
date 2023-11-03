package com.xyh.service;


public interface EmailService {

    String sendCode(String userEmail);

    void sendRestPass(String userEmail,String pass);

}
