package lanmapping.server;

import lanmapping.vo.LanMappingVo;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Objects;

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
    public abstract String[] bulid(LanMappingVo lanMappingVo,String path, String suffix);
    /**
     * 日记打印
     * @param log
     */
    public abstract boolean logPrint(String log);

    /**
     * 执行程序
     * @param lanMappingVo  映射信息
     */
    public void start(LanMappingVo lanMappingVo) {
        try {
            System.out.println("局域网映射开始!!!");
            // 获取程序目录
            String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("lm")).getPath().substring(1);
            path = URLDecoder.decode(path, "UTF-8");
            // 根据操作系统获取执行后缀
            String suffix = System.getProperty("os.name").contains("Windows") ? ".exe" : "";
            // 构建配置文件
            String[] bulid = bulid(lanMappingVo, path, suffix);
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
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}