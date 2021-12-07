package lanmapping.command;

import lanmapping.service.LanMappingService;
import lanmapping.service.impl.NoneServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * 局域网映射执行类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@Order(100)
@Slf4j
public class LanMappingCommand implements CommandLineRunner {
    /**
     * 获取相应实现实例
     */
    @Autowired
    private LanMappingService lanMappingService;
    @Override
    public void run(String... args) {
        // 判断是否为无效类
        if (!(lanMappingService instanceof NoneServiceImpl)) {
            lanMappingService.exec();
        }
    }
}