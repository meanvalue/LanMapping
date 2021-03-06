为项目免费提供快速内网穿透服务（省去外网部署即可快速测试）

### 当前最新版本

```xml
<dependency>
     <groupId>io.github.meanvalue.lanmapping</groupId>
     <artifactId>LanMapping-spring-boot-starter</artifactId>
     <version>1.8</version>
</dependency>
```

### 使用教程

```java
@SpringBootApplication
// 使用一条注解即可快速完成外网映射，外网地址会输出在控制台
@EnableLanMapping(template = LanMappingTemplate.DING)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

### 模板（LanMappingTemplate）

```java
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
```

### 详细配置

#### frp

```yaml
lm:
  frp:  # frp模式
    enable: true  # 启用
    server-addr: frp.meanvalue.top
    token: freefrp.net
    server-port: 7000
    clients: # 集合 可以填多个 需添加 -
      - type: http          # 协议
        local-ip: 127.0.0.1 # 本地IP
        local-port: 8080    # 本地端口
        custom-domains: abcdef.frp.meanvalue.top # 自定义域名+.${lm.frp.server-addr}
        remote-port: 80     # 远程端口
```

#### ding

```yaml
lm:
  ding: # ding模式
    enable: true  # 启用
    subdomain: abcdef  # 子域名
    port: 8082  # 本地端口
```

#### ngrok(开发中)
