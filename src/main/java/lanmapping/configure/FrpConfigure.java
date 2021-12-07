package lanmapping.configure;

import lanmapping.template.LmType;
import lanmapping.utils.IniFileEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
/**
 * frp配置类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "lm.frp")
public class FrpConfigure extends LanMapping{
    /**
     * 服务提供商提供的 frp 服务器 IP 地址或者域名地址
     */
    private String server_addr;
    /**
     * 服务提供商提供的 frp 服务端口号
     */
    private String server_port = "7000";
    /**
     * 服务提供商提供的密码
     */
    private String token;
    /**
     * 自定义参数
     */
    private List<String> args=new ArrayList<>();
    private List<FrpClient> clients=new ArrayList<>();
    public List<IniFileEntity> parse() {
        List<IniFileEntity> list = new ArrayList<>();
        String section = "common";
        list.add(new IniFileEntity(section, "server_addr", server_addr));
        list.add(new IniFileEntity(section, "server_port", server_port));
        list.add(new IniFileEntity(section, "token", token));
        for (String s : args) {
            String[] strs = s.split("=");
            if (strs.length == 2) {
                list.add(new IniFileEntity(section, strs[0], strs[1]));
            }
        }
        for (FrpClient c : clients) {
            section = c.type.toString()+"_"+c.custom_domains;
            list.add(new IniFileEntity(section, "type", c.type.toString()));
            list.add(new IniFileEntity(section, "local_ip", c.local_ip));
            list.add(new IniFileEntity(section, "local_port", c.local_port));
            list.add(new IniFileEntity(section, "remote_port", c.remote_port));
            list.add(new IniFileEntity(section, "custom_domains", c.custom_domains));
            System.out.println(c.type.toString() + "://" + c.custom_domains + ":" + c.remote_port);
            for (String s : c.args) {
                String[] strs = s.split("=");
                if (strs.length == 2) {
                    list.add(new IniFileEntity(section, strs[0], strs[1]));
                }
            }
        }
        return list;
    }
    @Data
    @ConfigurationProperties(prefix = "lm.frp.clients")
    public static class FrpClient {
        /**
         * 协议类型 : 确保本条穿透服务使用此协议能够在内网正常使用或访问.例如,尝试在本地访问 http://内网IP:内网端口 确保能够正常浏览.
         */
        private LmType type = LmType.HTTP;
        /**
         * 内网 IP : 本地服务所在设备的内网 IP 地址.由于 frp 客户端有可能安装在 docker 容器中,所以请不要使用 127.0.0.1 来表示本机 IP.
         */
        private String local_ip = "0.0.0.0";
        /**
         * 本地端口 : 本地服务的端口号,.
         */
        private String local_port = "8080";
        private String remote_port = "80";
        private String custom_domains;
        /**
         * 自定义参数
         */
        private List<String> args=new ArrayList<>();
    }
}