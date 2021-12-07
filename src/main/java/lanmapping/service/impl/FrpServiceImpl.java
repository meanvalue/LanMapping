package lanmapping.service.impl;

import lanmapping.configure.FrpConfigure;
import lanmapping.service.LanMappingService;
import lanmapping.template.LmType;
import lanmapping.utils.IniFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
/**
 * FRP实现映射类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
@Slf4j
public class FrpServiceImpl extends LanMappingService{
    /**
     * frp配置类
     */
    @Autowired
    private FrpConfigure frpConfigure;

    /**
     * 执行方法
     */
    @Override
    public void exec() {
        try {
            log.info("LanMapping start!!!");
            if (!"none".equals(this.getName())) {
                String serverAddr = this.getName() + ".cskt.xyz";
                build(serverAddr, "7000", "freefrp.net", LmType.HTTP, "127.0.0.1", this.getPort(), "80", this.getSubdomain() + "." + serverAddr);
            } else if (frpConfigure.isEnable()) {
                if (frpConfigure.getClients().size() == 0) {
                    log.error("当前frp配置错误！");
                    System.out.println("    #正确案例\n" +
                            "    clients:\n" +
                            "      - type: http\n" +
                            "        local-ip: 127.0.0.1\n" +
                            "        remote-port: 80\n" +
                            "        custom-domains: cskt.frp.cskt.xyz\n" +
                            "        local-port: 8082");
                    return;
                }
            } else {
                return;
            }
            this.setPath(URLDecoder.decode(this.getPath(), "UTF-8"));
            IniFileEntity.createIniFile(this.getPath()+"/frp/config.ini", frpConfigure.parse());
            this.setProcess(Runtime.getRuntime().exec(new String[]{this.getPath()+ "/frp/frpc"+this.getSuffix(), "-c", this.getPath()+  "/frp/config.ini"}));
            consumeInputStream(this.getProcess().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("LanMapping stop!!!");
        }

    }

    /**
     * 日记输出
     * @param is
     * @throws IOException
     */
    private void consumeInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String s = br.readLine();
        while (s != null) {
            if (s.contains("login to server failed")) {
                log.debug("登录服务器失败!");
                break;
            } else {
                log.info(s);
            }
            s = br.readLine();
        }
        this.getProcess().destroy();
    }

    /**
     * frp配置文件构建
     * @param serverAddr
     * @param serverPort
     * @param token
     * @param type
     * @param localIp
     * @param localPort
     * @param remotePort
     * @param custom_domains
     */
    public void build(String serverAddr, String serverPort, String token, LmType type, String localIp, String localPort, String remotePort, String custom_domains) {
        frpConfigure.setServer_addr(serverAddr);
        frpConfigure.setServer_port(serverPort);
        frpConfigure.setToken(token);

        FrpConfigure.FrpClient frpClient = new FrpConfigure.FrpClient();
        frpClient.setType(type);
        frpClient.setLocal_ip(localIp);
        frpClient.setLocal_port(localPort);
        frpClient.setRemote_port(remotePort);

        frpClient.setCustom_domains(custom_domains);
        frpConfigure.getClients().clear();
        frpConfigure.getClients().add(frpClient);
    }
}