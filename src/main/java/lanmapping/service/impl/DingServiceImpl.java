package lanmapping.service.impl;

import lanmapping.configure.DingConfigure;
import lanmapping.service.LanMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;

/**
 * 钉钉内网穿透实现类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
@Slf4j
public class DingServiceImpl extends LanMappingService{
    /**
     * 钉钉映射配置类
     */
    @Autowired
    private DingConfigure dingConfigure;

    /**
     * 执行方法
     */
    @Override
    public void exec() {
        try {
            log.info("LanMapping start!!!");
            if (!"none".equals(this.getName())) {
                dingConfigure.setSubdomain(this.getSubdomain());
                dingConfigure.setPort(this.getPort());
                System.out.println("http://"+this.getSubdomain()+".vaiwan.com");
            } else if (dingConfigure.isEnable()) {
                System.out.println("http://"+dingConfigure.getSubdomain()+".vaiwan.com");
            } else {
                return;
            }
            this.setPath(URLDecoder.decode(this.getPath(), "UTF-8"));
            this.setProcess(Runtime.getRuntime().exec(new String[]{this.getPath() + "/ding/ding" + this.getSuffix(), "-config=" + this.getPath() + "/ding/ding.cfg", "-subdomain=" + dingConfigure.getSubdomain(), dingConfigure.getPort()}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}