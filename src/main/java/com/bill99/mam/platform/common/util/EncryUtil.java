package com.bill99.mam.platform.common.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryUtil {

    public static String md5Encry(String src) {
        String rel = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            rel = base64en.encode(md5.digest(src.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException |  UnsupportedEncodingException e){
            // TODO: 2018/3/12  
        }
        return rel;
    }
}
