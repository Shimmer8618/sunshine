package com.sunshine.http_proxy.job;

import com.sunshine.http_proxy.pool.ObjectPool;
import com.sunshine.http_proxy.pool.ProxyHost;
import com.sunshine.http_proxy.utils.HttpProxyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Mkdirjob {
	public static Logger log = LoggerFactory.getLogger(Mkdirjob.class);
	@Autowired
	private HttpProxyConfig httpProxyConfig;
	
	/**
	 * 每五分钟检测是否需要创建对象
	 */
	@Scheduled(cron = "30 * * * * ?")
	public void startJob() {
		ObjectPool pool = ObjectPool.getObjecPool(ProxyHost.class, httpProxyConfig);
		try {
			log.info("*************给池中添加ip对象*************");
			while (pool.getNumIdle()+pool.getNumActive() < pool.getMaxIdle()) {
				log.info("当前池中空闲对象个数：  "+pool.getNumIdle());
				log.info("池中请允许最大空闲对象个值：  "+pool.getMaxIdle());
				pool.makeObject();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 每3分钟触发检测
	 */
	@Scheduled(cron = "3 * * * * ?")
    public void getnumJob() {
		ObjectPool pool = ObjectPool.getObjecPool(ProxyHost.class, httpProxyConfig);
		try {
			log.info("*************检测池中对象是否有效*************");
			for(int i=0;i<pool.getNumIdle();i++){
				Object obj = pool.borrowObject();
				log.info("池中有效的对象的数目：   " + pool.getNumIdle());
				log.info("池中借出的对象的总数：   " + pool.getNumActive());
				log.info("正在检测池中对象：  "+obj.toString());
				pool.returnObject(obj);
			}
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}

	}
	public ProxyHost getProxyHost(){
		ObjectPool pool = ObjectPool.getObjecPool(ProxyHost.class, httpProxyConfig);
		ProxyHost proxyHost;
		int i = 0;
		log.info("*************调用池中对象*************");
		do {
			proxyHost = pool.borrowObject();
			pool.returnObject(proxyHost);
			i++;
		} while (proxyHost==null&&i<3);
		if (null == proxyHost) {
			log.error("池中暂无对象");
			return new ProxyHost();
		}
		log.info("已调用对象：  "+proxyHost.toString());
		return proxyHost;
	}
}