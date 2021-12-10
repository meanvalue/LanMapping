package io.github.meanvalue.lanmapping.utils;

import java.io.File;
import java.util.Random;

/**
 * 工具类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
public class LmUtil {
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

    /**
     * 删除目录
     * @param file
     */
    public static void deleteFileOrDirectory(File file) {
        if (null != file) {

            if (!file.exists()) {
                return;
            }

            int i;
            // file 是文件
            if (file.isFile()) {
                boolean result = file.delete();
                // 限制循环次数，避免死循环
                for(i = 0; !result && i++ < 10; result = file.delete()) {
                    // 垃圾回收
                    System.gc();
                }

                return;
            }

            // file 是目录
            File[] files = file.listFiles();
            if (null != files) {
                for(i = 0; i < files.length; ++i) {
                    deleteFileOrDirectory(files[i]);
                }
            }

            file.delete();
        }

    }
}
