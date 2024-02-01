package com.wbu.staff.common.util;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {

    public static String decryptAES( String key, String secret) throws Exception {
        // 创建解密对象
        Cipher cipher = Cipher.getInstance("AES");
        //创建解密规则
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"AES");
        // 初始化对象
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        // 解密-->密文转码过了，必需解码
        byte[] bytes = cipher.doFinal(Base64.decode(secret));
        return new String(bytes);
    }

    /**
     * 加密 AES
     * @param key key
     * @param input 原文
     */
    public static String encryptAES( String key, String input) throws Exception  {
        // 创建加密对象 1.加密算法
        Cipher cipher = Cipher.getInstance("AES");
        // 创建加密规则 1.表示key的字节 2.表示加密的类型
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        // 初始化 1.模式：加密、解密 模式 2.加密规则、解密规则
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        // 完成加密或者解密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 必需使用base64进行转码。不然会乱码
        String encode = Base64.encode(bytes);
        return encode;
    }
}
