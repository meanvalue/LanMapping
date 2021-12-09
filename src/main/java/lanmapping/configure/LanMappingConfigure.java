package lanmapping.configure;

import lanmapping.EnableLanMapping;
import lanmapping.factory.LanMappingFactory;
import lanmapping.vo.LanMappingVo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置注入类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@Configuration
@EnableConfigurationProperties({FrpConfigure.class,FrpConfigure.FrpClient.class, DingConfigure.class})
public class LanMappingConfigure {
    @Bean
    public LanMappingFactory lanMappingFactory() {
        return new LanMappingFactory();
    }

    /**
     * 实例化映射信息类
     * @param applicationContext
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public LanMappingVo lanMappingVo(ApplicationContext applicationContext) {
        LanMappingVo vo = new LanMappingVo();
        // 获取使用注解的bean信息
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableLanMapping.class);
        if (beanNames.length > 0) {
            // 查找并获取第一个注解信息
            EnableLanMapping lanMapping = applicationContext.findAnnotationOnBean(beanNames[0], EnableLanMapping.class);
            assert lanMapping != null;
            vo.setName(lanMapping.template().getName());
            vo.setServerAddr(lanMapping.template().getServerAddr());
            vo.setServerPort(lanMapping.template().getServerPort());
            vo.setToken(lanMapping.template().getToken());
            vo.setPortInfo(lanMapping.template().getPortInfo());
            vo.setType(lanMapping.type());
            vo.setSubdomain(lanMapping.subdomain());
            vo.setLocalIp(lanMapping.localIp());
            vo.setLoaclPort(lanMapping.loaclPort());
            vo.setRemotePort(lanMapping.remotePort());
        }
        return vo;
    }
}