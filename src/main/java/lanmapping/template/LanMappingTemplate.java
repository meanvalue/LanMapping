package lanmapping.template;

/**
 * 模板枚举类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
public enum LanMappingTemplate {
    /**
     * 使用配置文件
     */
    NONE("none"),
    /**
     * 免费 FRP 内网穿透服务（国内）
     * frp.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP("frp"),
    /**
     * 免费 FRP 内网穿透服务（韩国 - 首尔）
     * frp1.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP1("frp1"),
    /**
     * 免费 FRP 内网穿透服务（韩国 - 首尔）
     * frp2.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP2("frp2"),
    /**
     * 免费 FRP 内网穿透服务（韩国 - 首尔）
     * frp3.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP3("frp3"),
    /**
     * 免费 FRP 内网穿透服务（美国 - 纽约）
     * frp4.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP4("frp4"),
    /**
     * 钉钉免费内网穿透
     * vaiwan.com
     */
    DING("ding"),
    /**
     * 暂未实现
     * ngrok官网（https://ngrok.com/） 美国
     * ngrok.io
     */
    NGROK("ngrok"),
    ;
    private final String template;
    LanMappingTemplate(String template) {
        this.template = template;
    }
    public String getTemplate() {
        return template;
    }
}