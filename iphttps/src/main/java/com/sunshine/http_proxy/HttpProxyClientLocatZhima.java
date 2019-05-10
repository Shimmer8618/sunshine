package com.sunshine.http_proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunshine.http_proxy.utils.HttpClientUtil;
import org.apache.commons.httpclient.HttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpProxyClientLocatZhima {
	private static final String BAIDU_PATH = "http://www.baidu.com";
	private static boolean flag = false;
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		String path = "http://webapi.http.zhimacangku.com/getip";
		map.put("num", "1");
		map.put("type", "2");
		map.put("pro", "420000");
		map.put("city", "421000");
		map.put("yys", "0");
		map.put("port", "1");
		map.put("pack", "51524");
		map.put("amount", "1");
		map.put("ts", "1");
		map.put("ys", "1");
		map.put("cs", "1");
		map.put("lb", "1");
		map.put("sb", "0");
		map.put("pb", "4");
		map.put("mr", "1");
		map.put("regions", "");
		JSONObject zhima = getHttpClientProxyIpZhiMa8(path,map);
		System.err.println(zhima.toString());
	}

	/**
	 * 获取代理ip
	 * @param path
	 * @param map
	 * @return
	 */
	public static JSONObject getHttpClientProxyIpZhiMa8(String path, Map<String, String> map) {
		JSONObject json = new JSONObject();
		List list = new ArrayList<HashMap<String, String>>();
		do {
			//String result = HttpClientUtil.httpGet(path, map);
			String result = "{\"code\":0,\"success\":true,\"msg\":\"0\",\"data\":[{\"ip\":\"121.61.171.31\",\"port\":4271,\"expire_time\":\"2019-05-01 22:04:26\",\"city\":\"湖北省荆州市\",\"isp\":\"电信\"}]}";
			System.out.println(result);
			List<String> parseArray = JSON.parseArray(JSON.parseObject(result).getString("data"), String.class);
			for (Object object : parseArray) {
				String ip = JSON.parseObject(result).getJSONArray("data").getJSONObject(0).getString("ip");
				String port = JSON.parseObject(result).getJSONArray("data").getJSONObject(0)
						.getString("port");
				String id = JSON.parseObject(result).getJSONArray("data").getJSONObject(0).getString("id");
				Map<String, String> hashMap = new HashMap<String, String>();
				Map<String, Integer> map1 = new HashMap<String, Integer>();
				map1.put(ip, Integer.valueOf(port));
				checkProxyIp(map1,BAIDU_PATH);

				if (flag) {
					hashMap.put("ip", ip);
					hashMap.put("port", port);
					list.add(hashMap);
					flag = false;
				}
			}

		} while (list.size() < 1);

		json.put("status", 0);
		json.put("data", list);
		return json;
	}
	
	/**
	 * 批量代理IP有效检测
	 * 
	 * @param proxyIpMap
	 * @param reqUrl
	 */
	public static void checkProxyIp(Map<String, Integer> proxyIpMap, String reqUrl) {

		for (String proxyHost : proxyIpMap.keySet()) {
			Integer proxyPort = proxyIpMap.get(proxyHost);
			int statusCode = 0;
			try {
				HttpClient httpClient = new HttpClient();
				httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);

				// 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
				int connectionTimeout = 10000;
				// 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
				int soTimeout = 30000;
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

				String httpGet = HttpClientUtil.httpGet(reqUrl, new HashMap<>());
				System.out.println(httpGet);
				statusCode = httpGet.indexOf("html");
			} catch (Exception e) {
				System.err.println("ip " + proxyHost + " is not aviable");
			}

			if (statusCode > 0) {
				flag = true;
				System.err.println(Thread.currentThread().getName() + "   " + proxyHost + ":" + proxyPort + " is ok");
				System.err.format("%s:%s-->%s/n", proxyHost, proxyPort, statusCode);
			}

		}
	}

	/**
	 * 单个代理IP有效检测
	 * 
	 * @param IP
	 * @param post
	 */
	public static void createIPAddress(String ip, int port) {
		URL url = null;
		try {
			url = new URL(BAIDU_PATH);
		} catch (MalformedURLException e) {
			System.out.println("url invalidate");
		}
		InetSocketAddress addr = null;
		addr = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
		InputStream in = null;
		try {
			URLConnection conn = url.openConnection(proxy);
			conn.setConnectTimeout(1000);
			in = conn.getInputStream();
		} catch (Exception e) {
			System.err.println(Thread.currentThread().getName() + "  ip " + ip + " : " + port + "   is not aviable");// 异常IP
		}
		String s = convertStreamToString(in);
		System.out.println(s);
		// System.out.println(s);
		if (s.indexOf("html") > 0) {// 有效IP
			System.out.println(Thread.currentThread().getName() + "   " + ip + ":" + port + " is ok");
			flag = true;
		}
	}

	public static String convertStreamToString(InputStream is) {
		if (is == null)
			return "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
}
