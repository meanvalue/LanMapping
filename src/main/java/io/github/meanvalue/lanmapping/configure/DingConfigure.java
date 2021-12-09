package io.github.meanvalue.lanmapping.configure;

import io.github.meanvalue.lanmapping.utils.RandomUtil;
import io.github.meanvalue.lanmapping.server.LanMappingServer;
import io.github.meanvalue.lanmapping.vo.LanMappingVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * 钉钉配置类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "lm.ding")
public class DingConfigure extends LanMappingServer {
    /**
     * 子域名
     */
    private String subdomain;
    /**
     * 本地端口号
     */
    private String port;



    @Override
    public String[] bulid(LanMappingVo lanMappingVo,String path, String suffix) {
        if (!this.isEnable()) {
            if (StringUtils.isEmpty(lanMappingVo.getSubdomain())) {
                // 使用随机字符当子域名
                this.subdomain = RandomUtil.getRandomString(6);
            } else {
                // 使用注解配置的子域名
                this.subdomain = lanMappingVo.getSubdomain();
            }
            // 使用注解配置的端口号
            this.port = lanMappingVo.getLoaclPort();
        }
        System.out.println(lanMappingVo.getType().toString()+"://"+this.subdomain+".vaiwan.com");
        return new String[]{path + "/ding/ding" + suffix, "-config=" + path + "/ding/ding.cfg", "-subdomain=" + this.subdomain, this.port};
    }

    @Override
    public boolean logPrint(String log) {
        return true;
    }
}