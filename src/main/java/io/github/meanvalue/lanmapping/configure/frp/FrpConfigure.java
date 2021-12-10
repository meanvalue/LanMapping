package io.github.meanvalue.lanmapping.configure.frp;

import io.github.meanvalue.lanmapping.server.LanMappingServer;
import io.github.meanvalue.lanmapping.template.LmType;
import io.github.meanvalue.lanmapping.utils.IniFileEntity;
import io.github.meanvalue.lanmapping.utils.LmUtil;
import io.github.meanvalue.lanmapping.vo.LanMappingVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
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
public class FrpConfigure extends LanMappingServer {
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
     * 子模块属性
     */
    private List<FrpClient> clients=new ArrayList<>();
    /**
     * ini解析
     * @return
     * @throws IllegalAccessException
     */
    public List<IniFileEntity> parse() {
        List<IniFileEntity> list = new ArrayList<>();
        try {
            String section = "common";
            // 通过反射获取属性
            for (Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 获取值
                Object obj = f.get(this);
                if (obj instanceof String) {
                    // 添加解析
                    list.add(new IniFileEntity(section, f.getName(), obj.toString()));
                } else {
                    // 子属性迭代解析
                    for (FrpClient frpClient : (List<FrpClient>) obj) {
                        frpClient.parse(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 构建文件
     * @param lanMappingVo
     * @param suffix
     * @param temp
     * @return
     */
    @Override
    public String[] bulid(LanMappingVo lanMappingVo, String suffix, File temp) {
        if (this.isEnable()) {

        } else {
            // 设置连接服务配置
            this.server_addr = lanMappingVo.getServerAddr();
            this.server_port = lanMappingVo.getServerPort();
            this.token = lanMappingVo.getToken();
            // 设置客户配置
            FrpClient frpClient = new FrpClient();
            if (StringUtils.isEmpty(lanMappingVo.getSubdomain())) {
                // 使用随机字符当子域名
                lanMappingVo.setSubdomain(LmUtil.getRandomString(6));
            }
            // 使用注解配置
            frpClient.setType(lanMappingVo.getType());
            frpClient.setLocal_ip(lanMappingVo.getLocalIp());
            frpClient.setLocal_port(lanMappingVo.getLoaclPort());
            frpClient.setRemote_port(lanMappingVo.getRemotePort());
            frpClient.setCustom_domains(lanMappingVo.getSubdomain() + "." + lanMappingVo.getServerAddr());
            this.clients.clear();
            this.clients.add(frpClient);
        }
        save(temp,"frp",suffix);
        // 生成ini配置文件
        IniFileEntity.createIniFile(temp+ "/frp.ini", this.parse());
        return new String[]{temp+"/frp"+suffix, "-c", temp + "/frp.ini"};
    }

    @Override
    public boolean logPrint(String log) {
        if (log.contains("login to server failed")) {
            System.out.println("局域网映射失败!!!");
            return false;
        }
        System.out.println(log);
        return true;
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
         * ini解析
         * @param list
         * @throws IllegalAccessException
         */
        public void parse(List<IniFileEntity> list) throws IllegalAccessException {
            // 拼接远程地址
            String section = type.toString() + "_" + custom_domains+":" + remote_port;
            // 通过反射获取属性
            for (Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 获取值
                list.add(new IniFileEntity(section, f.getName(),f.get(this).toString()));
            }
            // 输出远程地址
            System.out.println(type.toString() + "://" + custom_domains+":" + remote_port);
        }
    }
}