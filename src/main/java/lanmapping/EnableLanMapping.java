package lanmapping;

import lanmapping.template.LanMappingTemplate;
import lanmapping.command.LanMappingCommand;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
/**
 * 启用局域网映射
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({LanMappingCommand.class})
public @interface EnableLanMapping {
    /**
     * 使用模板进行简单映射，默认使用配置文件
     */
    LanMappingTemplate template() default LanMappingTemplate.NONE;

    /**
     * 子域名，不填则随机生成
     */
    String subdomain() default "";
}