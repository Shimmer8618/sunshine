package com.sunshine.http_proxy.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * 从配置文件中读取数据映射到map
 * 注意：必须实现set方法
 */
@Configuration
@ConfigurationProperties(prefix = "ip-proxy")
public class HttpProxyConfig {
	private int maxActive;
	private int maxIdle;
	private int maxWait;
	private int testTime;
	private int numTestsPerEvictionRun;
	private int iPType;
	private boolean testOnReturn;
	private boolean testOnBorrow;
	private boolean lifo;
	private String httpPath;
	private boolean testWhileIdle;
	private long timeBetweenEvictionRunsMillis;
	private Map<String, String> httpProxy = new HashMap<>();
	
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isLifo() {
		return lifo;
	}
	public void setLifo(boolean lifo) {
		this.lifo = lifo;
	}
	public String getHttpPath() {
		return httpPath;
	}
	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}
	public Map<String, String> getHttpProxy() {
		return httpProxy;
	}
	public void setHttpProxy(Map<String, String> httpProxy) {
		this.httpProxy = httpProxy;
	}
	public int getTestTime() {
		return testTime;
	}
	public void setTestTime(int testTime) {
		this.testTime = testTime;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public int getiPType() {
        return iPType;
    }

    public void setiPType(int iPType) {
        this.iPType = iPType;
    }
}
