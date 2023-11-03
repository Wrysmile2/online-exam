package com.xyh.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对于HTML标签的去除帮助类
 */
public class HTMLUtil {

    public static String clear(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html = "<[^>]+>";

        String spaceStr = "&nbsp;";
        String ltStr = "&lt;";
        String gtStr = "&gt;";
        String andStr = "&amp;";

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        htmlStr = htmlStr.replaceAll(spaceStr,"");
        htmlStr = htmlStr.replaceAll(gtStr,">");
        htmlStr =htmlStr.replaceAll(ltStr,"<");
        htmlStr =htmlStr.replaceAll(andStr,"&");

        return htmlStr.trim();
    }
}
