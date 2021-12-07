package lanmapping.configure;

import lanmapping.EnableLanMapping;
import lanmapping.service.LanMappingService;
import lanmapping.service.impl.DingServiceImpl;
import lanmapping.service.impl.FrpServiceImpl;
import lanmapping.service.impl.NoneServiceImpl;
import lanmapping.utils.RandomUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    /**
     * 钉钉配置文件注入
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "lm.ding",name = "enable",havingValue = "true")
    public LanMappingService dingService() {
        return new DingServiceImpl().setName("none");
    }

    /**
     * frp配置文件注入
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "lm.frp",name = "enable",havingValue = "true")
    public LanMappingService frpService() {
        return new FrpServiceImpl().setName("none");
    }

    /**
     * 未开启配置文件注入时进行注解模板注入
     * @param applicationContext
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public LanMappingService lanMappingService(ApplicationContext applicationContext) {
        LanMappingService lanMappingService = null;
        // 获取使用注解的bean信息
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableLanMapping.class);
        for (String name : beanNames) {
            // 查找并获取注解信息
            EnableLanMapping enableLanMapping = applicationContext.findAnnotationOnBean(name, EnableLanMapping.class);
            // 获取模板信息
            String template = enableLanMapping.template().getTemplate();
            if (template.contains("frp")) {
                // 使用frp模板
                lanMappingService = frpService();
            } else if (template.contains("ding")) {
                // 使用钉钉模板
                lanMappingService = dingService();
            } else {
                // 未使用模板，也没有使用配置类，无效化
                return new NoneServiceImpl();
            }
            // 获取子域名
            String subdomain = enableLanMapping.subdomain();
            // 未填时对域名进行生成随机字符
            subdomain = "".equals(subdomain) ? RandomUtil.getRandomString(6) : subdomain;
            // 写入相应子域名
            lanMappingService.setSubdomain(subdomain);
            // 写入模板信息
            lanMappingService.setName(template);
        }
        return lanMappingService;
    }
}