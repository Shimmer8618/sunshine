package com.sunshine.http_proxy.pool;

import java.lang.reflect.Method;
import java.util.Date;

import com.sunshine.http_proxy.HttpProxyClientLocat;
import com.sunshine.http_proxy.utils.HttpProxyConfig;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunshine.http_proxy.utils.DateUtil;

@SuppressWarnings({"unchecked", "rawtypes" })
public class ObjectPoolFactory implements PoolableObjectFactory {
	public static Logger log = LoggerFactory.getLogger(ObjectPoolFactory.class);
	private Class cls;
	private HttpProxyConfig httpProxyConfig;
	private static final String INIT_METHOD = "clearObject";// 有状态对象恢复初始化的方法

	public ObjectPoolFactory(Class cls, HttpProxyConfig httpProxyConfig) {
		this.cls = cls;
		this.httpProxyConfig = httpProxyConfig;
	}

	public void activateObject(Object arg0) throws Exception {
		log.info("有状态对象恢复初始化");
		try {
			cls.getDeclaredMethod(INIT_METHOD).invoke(arg0);// 有状态对象恢复初始化
		} catch (Exception e) {
		}
	}

	public void destroyObject(Object arg0) throws Exception {
		log.info("销毁要过期IP对象："+arg0.toString());
	}

	public Object makeObject() throws Exception {
		synchronized (this){
			JSONObject json = HttpProxyClientLocat.getHttpClientProxyIpZhiMa8(httpProxyConfig);
			Object object = JSON.parseObject(json.getJSONArray("data").getJSONObject(0).toString(), cls);
			log.info("创建新IP对象："+object.toString());
			return object;
		}
	}

	public void passivateObject(Object arg0) throws Exception {
	}

	public boolean validateObject(Object arg0) {
		//log.info("验证对象："+arg0.toString());
		try {
			String fieldName = "expireTime";
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = arg0.getClass().getMethod(getter, new Class[] {});
			String value = (String) method.invoke(arg0, new Object[] {});
			Date date = DateUtil.parse(value, DateUtil.TIME_PATTERN_DEFAULT);
			log.info("距离截止有效期还剩：" + DateUtil.minsBetween(new Date(), date)+" 分钟");
			if (DateUtil.minsBetween(new Date(), date) <= httpProxyConfig.getTestTime()) {
				log.info(arg0.toString() + " 即将被销毁");
				return false;
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return true;
	}
}
