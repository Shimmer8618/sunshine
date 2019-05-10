package com.sunshine.http_proxy.pool;

import com.sunshine.http_proxy.utils.HttpProxyConfig;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ObjectPool {
	public static Logger log = LoggerFactory.getLogger(ObjectPool.class);
	private static ObjectPool objectPool;
	private GenericObjectPool pool;

	private ObjectPool() {}

	private ObjectPool(Class cls, HttpProxyConfig httpProxyConfig) {
		this.pool = new GenericObjectPool(new ObjectPoolFactory(cls, httpProxyConfig));
		pool.setMaxActive(httpProxyConfig.getMaxActive());// 最大活动对象
		pool.setMaxIdle(httpProxyConfig.getMaxIdle());// 最大空闲对象
		pool.setMaxWait(httpProxyConfig.getMaxWait());// 最大等待时间
		pool.setTestOnReturn(httpProxyConfig.isTestOnReturn());
		pool.setTestOnBorrow(httpProxyConfig.isTestOnBorrow());
		pool.setLifo(httpProxyConfig.isLifo());
		pool.setTimeBetweenEvictionRunsMillis(httpProxyConfig.getTimeBetweenEvictionRunsMillis());
		pool.setNumTestsPerEvictionRun(httpProxyConfig.getNumTestsPerEvictionRun());
		pool.setTestWhileIdle(true);
	}

	public static ObjectPool getObjecPool(Class cls, HttpProxyConfig httpProxyConfig) {
		if (objectPool == null) {
			synchronized (ObjectPool.class) {
				if (objectPool == null) {
					objectPool = new ObjectPool(ProxyHost.class, httpProxyConfig);
				}
			}
		}
		return objectPool;
	}

	/**
	 * 创建对象
	 * 
	 * @return
	 */
	public void makeObject() {
		try {
			log.info("创建对象");
			pool.addObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 池中取出对象
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> T borrowObject() {
		T obj = null;
		try {
			obj = (T) pool.borrowObject();
			log.info("获得对象");
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	/**
	 * 对象放回池中
	 * 
	 * @param obj
	 */
	public void returnObject(Object obj) {
		try {
			pool.returnObject(obj);
			log.info("返还对象");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 检测对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean validateObject(Object obj) {
		try {
			log.info("检测对象");
			return ((PoolableObjectFactory) pool).validateObject(obj);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * 销毁对象
	 * 
	 * @param obj
	 */
	public void destroyObject(Object obj) {
		try {
			pool.invalidateObject(obj);
			log.info("销毁对象");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 是否后进先出
	public void setLifo(boolean bool) {
		pool.setLifo(bool);
	}

	// 对象将在返回到后进行验证。
	public void setTestOnReturn(boolean bool) {
		pool.setTestOnReturn(bool);
	}

	// 是否在借用对象之前应该进行验证。
	public void setTestOnBorrow(boolean bool) {
		pool.setTestOnBorrow(bool);
	}

	// 返回最大活动对象
	public int getMaxActive() {
		return pool.getMaxActive();
	}

	// 返回最多可空闲对象
	public int getMaxIdle() {
		return pool.getMaxIdle();
	}

	// 返回当前在此对象池中休眠的对象的数目。
	public int getNumIdle() {
		return pool.getNumIdle();
	}

	// 返回已经从此对象池中借出的对象的总数
	public int getNumActive() {
		return pool.getNumActive();
	}

	// 将当前对象池与参数中给定的PoolableObjectFactory相关联。如果在当前状态下，无法完成这一操作，会有一个IllegalStateException异常抛出。
	public void setFactory(PoolableObjectFactory factory) {
		pool.setFactory(factory);
	}

	// 清空对象池，并销毁对象池中的所有对象
	public void clear() {
		pool.clear();
	}

	// 关闭对象池
	public void close() {
		try {
			pool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}