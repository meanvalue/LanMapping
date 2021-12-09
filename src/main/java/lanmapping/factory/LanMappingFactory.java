package lanmapping.factory;

import lanmapping.server.LanMappingServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂方法
 * @author meanvalue
 * Date: 2021/12/8 11:02
 */
public class LanMappingFactory {
    @Autowired
    private Map<String, LanMappingServer> map = new HashMap<>();

    /**
     * 获取相应实例
     * @param name
     * @return
     */
    public LanMappingServer getService(String name) {
        for (String key : map.keySet()) {
            LanMappingServer lanMappingServer = map.get(key);
            if ((name!=null && key.contains(name)) || lanMappingServer.isEnable()) {
                return lanMappingServer;
            }
        }
        return null;
    }
}