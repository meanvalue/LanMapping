package lanmapping.vo;

import lanmapping.template.LmType;
import lombok.Data;

/**
 * 映射信息类
 * @author meanvalue
 * Date: 2021/12/9 12:02
 */
@Data
public class LanMappingVo {
    /**
     * 模板名
     */
    private String name;
    /**
     * 服务器连接地址
     */
    private String serverAddr;
    /**
     * 服务器连接端口
     */
    private String serverPort;
    /**
     * 服务器连接密码
     */
    private String token;
    /**
     * 服务器可用端口
     */
    private String portInfo;
    /**
     * 映射类型
     */
    private LmType type;
    /**
     * 子域名，不填则随机生成
     */
    private String subdomain;
    /**
     * 本地IP
     */
    private String localIp;
    /**
     * 本地端口,默认项目运行端口 ${server.port}
     */
    private String loaclPort;
    /**
     * 远程端口
     */
    private String remotePort;
}