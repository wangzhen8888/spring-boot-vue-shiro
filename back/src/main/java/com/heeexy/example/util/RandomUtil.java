package com.heeexy.example.util;

import java.util.UUID;


public class RandomUtil {
    /**
     * 生成前缀+32位编码 用作id
     *
     * @return string
     */
    public static String genarateId(String prefix) {
        String uuid = prefix + UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
