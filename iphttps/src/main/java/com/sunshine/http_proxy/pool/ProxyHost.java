package com.sunshine.http_proxy.pool;
/**
 * 代理对象
 *
 * @author hasee
 */

import java.io.Serializable;

public class ProxyHost implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    private String ip;
    private Integer port;
    private String creaTime;
    private String expireTime;
    private String city;
    private String isp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCreaTime() {
        return creaTime;
    }

    public void setCreaTime(String creaTime) {
        this.creaTime = creaTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    @Override
    public String toString() {
        return "ip：" + ip + " 端口：" + port + "  到期时间：" + expireTime + " 创建时间：" + creaTime + " 城市：" + city + " isp：" + isp;
    }
}
