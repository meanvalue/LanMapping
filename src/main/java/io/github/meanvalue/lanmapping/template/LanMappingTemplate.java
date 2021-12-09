package io.github.meanvalue.lanmapping.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 模板枚举类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LanMappingTemplate {
    /**
     * 使用配置文件
     */
    NONE(),
    /**
     * 韩国 - 首尔
     * frp.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP_FRP("frp","frp.meanvalue.top","7000","freefrp.net","80,443,10001-50000"),
    /**
     * 韩国 - 首尔
     * frp1.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP_FRP1("frp","frp1.meanvalue.top","7000","freefrp.net","80,443,10001-50000"),
    /**
     * 韩国 - 首尔
     * frp2.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP_FRP2("frp","frp2.meanvalue.top","7000","freefrp.net","80,443,10001-50000"),
    /**
     * 韩国 - 首尔
     * frp3.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP_FRP3("frp","frp3.meanvalue.top","7000","freefrp.net","80,443,10001-50000"),
    /**
     * 美国 - 纽约
     * frp4.freefrp.net
     * 连接端口 ：7000   密码 ：freefrp.net
     * 开放端口 ：80 / 443 , 10001 - 50000
     */
    FRP_FRP4("frp","frp4.meanvalue.top","7000","freefrp.net","80,443,10001-50000"),
    /**
     * 美国 - 洛杉矶
     * frps.lu8.win
     * 连接端口 ：7000   密码 ：frp888
     * 开放端口 ：1000-65535
     */
    FRP_LU8("frp","frps.lu8.win","7100","frp888","1000-65535"),
    /**
     * 钉钉免费内网穿透
     * vaiwan.com
     */
    DING("ding","vaiwan.com","443","","80,443"),
    ;
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
}