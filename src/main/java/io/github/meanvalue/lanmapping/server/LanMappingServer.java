package io.github.meanvalue.lanmapping.server;

import io.github.meanvalue.lanmapping.EnableLanMapping;
import io.github.meanvalue.lanmapping.utils.LmUtil;
import io.github.meanvalue.lanmapping.vo.LanMappingVo;

import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;

/**
 * 局域网映射服务类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
public abstract class LanMappingServer {
    /**
     * 启用
     */
    private boolean enable = false;
    private Process process;
    /**
     * 构建配置文件
     */
    public abstract String[] bulid(LanMappingVo lanMappingVo,String suffix,File temp);
    /**
     * 日记打印
     * @param log
     */
    public abstract boolean logPrint(String log);

    /**
     * 保存临时文件
     * @param temp
     * @param file
     * @param suffix
     */
    public void save(File temp,String file,String suffix) {
        // 获取程序目录
        InputStream resourceAsStream = EnableLanMapping.class.getClassLoader().getResourceAsStream("lm/" + file + "/" + file + suffix);
        try {
            Files.copy(resourceAsStream, new File(temp, file+suffix).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 执行程序
     * @param lanMappingVo  映射信息
     */
    public void start(LanMappingVo lanMappingVo) {
        try {
            System.out.println("局域网映射开始!!!");
            // 获取临时目录
            File temp = new File(System.getProperty("java.io.tmpdir"),"LanMapping");
            // 删除临时目录
            LmUtil.deleteFileOrDirectory(temp);
            // 创建临时目录
            temp.mkdirs();
            // 根据操作系统获取执行后缀
            String suffix = System.getProperty("os.name").contains("Windows") ? ".exe" : "";
            // 构建配置文件
            String[] bulid = bulid(lanMappingVo, suffix,temp);
            // 执行命令程序
            process = Runtime.getRuntime().exec(bulid);
            // 获取日记流
            logInputStream(process.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭进程
            if (this.process != null) {
                this.process.destroy();
            }
            System.out.println("局域网映射结束!!!");
        }
    }

    /**
     * 获取打印日记
     * @param is
     */
    public void logInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String log = null;
        while ((log = br.readLine()) != null) {
            // 打印日记
            if (!logPrint(log)) {
                return;
            }
        }
    }
    /**
     * 销毁方法
     */
    @PreDestroy
    public void destroy() {
        if (this.process != null) {
            this.process.destroy();
            System.out.println("局域网映射销毁!!!");
        }
        // 删除临时目录
        File temp = new File(System.getProperty("java.io.tmpdir"), "LanMapping");
        LmUtil.deleteFileOrDirectory(temp);
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}