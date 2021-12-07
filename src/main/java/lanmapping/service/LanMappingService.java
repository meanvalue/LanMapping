package lanmapping.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * 局域网映射服务
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
@Data
@Slf4j
public abstract class LanMappingService {
    /**
     * 子域名
     */
    private String subdomain;

    /**
     * 模式名称
     */
    private String name;
    /**
     * 模式信息
     */
    private String info;
    /**
     * 程序路径
     */
    private String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("lm")).getPath().substring(1);
    /**
     * 本地端口号
     */
    @Value("${server.port}")
    private String port;
    /**
     * 执行文件后缀
     */
    private String suffix = System.getProperty("os.name").contains("Windows")?".exe":"";
    /**
     * 执行方法
     */
    public abstract void exec();
    /**
     * process实例
     */
    private Process process;
    public LanMappingService setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 销毁方法
     */
    @PreDestroy
    public void destroy() {
        if (this.getProcess() != null) {
            this.getProcess().destroy();
            log.info("LanMapping destroy!!!");
        }
    }
}