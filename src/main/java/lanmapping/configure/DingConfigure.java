package lanmapping.configure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 钉钉配置类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "lm.ding")
public class DingConfigure extends LanMapping {
    /**
     * 子域名
     */
    private String subdomain;
    /**
     * 本地端口号
     */
    private String port;
}