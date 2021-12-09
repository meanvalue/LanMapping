package lanmapping.command;

import lanmapping.factory.LanMappingFactory;
import lanmapping.server.LanMappingServer;
import lanmapping.vo.LanMappingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * 局域网映射执行类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@Order(100)
public class LanMappingCommand implements CommandLineRunner {

    @Autowired
    private LanMappingVo lanMappingVo;
    @Autowired
    private LanMappingFactory lanMappingFactory;

    @Value("${server.port}")
    private String port;
    @Override
    public void run(String... args) {
        // 填入默认项目端口
        if (StringUtils.isEmpty(lanMappingVo.getLoaclPort())) {
            lanMappingVo.setLoaclPort(port);
        }
        // 设置默认远程端口
        if (StringUtils.isEmpty(lanMappingVo.getRemotePort())) {
            lanMappingVo.setRemotePort("80");
        }
        // 端口信息校验
        if (!StringUtils.isEmpty(lanMappingVo.getPortInfo())) {
            // 校验提示
            if (!portParse(lanMappingVo.getPortInfo(),Integer.parseInt(lanMappingVo.getRemotePort()))) {
                System.out.println("当前模板可能不支持当前远程端口!");
            }
        }
        // 通过工厂模式获取相应实例
        LanMappingServer service = lanMappingFactory.getService(lanMappingVo.getName());
        // 执行
        service.start(lanMappingVo);
    }

    /**
     * 端口信息校验
     * @param portInfo
     * @return
     */
    public boolean portParse(String portInfo, Integer port) {
        boolean info = false;
        String[] strs = portInfo.split(",");
        for (String str : strs) {
            String[] ports = str.split("-");
            if (port == Integer.parseInt(ports[0]) || ports.length > 1 && port >= Integer.parseInt(ports[0]) && port <= Integer.parseInt(ports[1])) {
                info = true;
            }
        }
        return info;
    }
}