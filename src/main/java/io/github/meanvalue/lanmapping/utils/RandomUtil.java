package io.github.meanvalue.lanmapping.utils;

import java.util.Random;

/**
 * 随机数工具类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
public class RandomUtil {
    /**
     * 生成随机字符
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
