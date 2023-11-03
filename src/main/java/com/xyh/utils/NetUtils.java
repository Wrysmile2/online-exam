package com.xyh.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络的帮助类
 */
public class NetUtils {

    /**
     * 得到服务器的ip地址
     * @return
     */
    public static String getServerIp(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }
}
