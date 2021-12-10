package io.github.meanvalue.lanmapping.configure.ding;

import io.github.meanvalue.lanmapping.server.LanMappingServer;
import io.github.meanvalue.lanmapping.utils.LmUtil;
import io.github.meanvalue.lanmapping.vo.LanMappingVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.io.File;

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


    /**
     * 构建文件
     * @param lanMappingVo
     * @param suffix
     * @param temp
     * @return
     */
    @Override
    public String[] bulid(LanMappingVo lanMappingVo,  String suffix, File temp) {
        if (!this.isEnable()) {
            if (StringUtils.isEmpty(lanMappingVo.getSubdomain())) {
                // 使用随机字符当子域名
                this.subdomain = LmUtil.getRandomString(6);
            } else {
                // 使用注解配置的子域名
                this.subdomain = lanMappingVo.getSubdomain();
            }
            // 使用注解配置的端口号
            this.port = lanMappingVo.getLoaclPort();
        }
        // 保存临时文件
        save(temp,"ding",suffix);
        save(temp,"ding",".cfg");
        System.out.println(lanMappingVo.getType().toString()+"://"+this.subdomain+".vaiwan.com");
        return new String[]{temp+"/ding" + suffix, "-config="+temp+"/ding.cfg", "-subdomain=" + this.subdomain, this.port};
    }

    @Override
    public boolean logPrint(String log) {
        return true;
    }
}