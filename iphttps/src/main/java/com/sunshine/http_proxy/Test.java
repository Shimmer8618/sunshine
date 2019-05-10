package com.sunshine.http_proxy;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sunshine.http_proxy.utils.HttpProxyConfig;


public class Test {
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
		 JSONObject zhima = HttpProxyClientLocat.getHttpClientProxyIp(new HttpProxyConfig());
		 System.err.println(zhima.toString());
		
//		Map<String, String> map = new HashMap<String, String>();
//		String path = "https://api.2808proxy.com/proxy/unify/get";
//		map.put("token", "A15Q4AYR900XTQYZHC28H4D07H7E8940");
//		map.put("amount", "1");
//		map.put("proxy_type", "http");
//		map.put("format", "json");
//		map.put("splitter", "rn");
//		map.put("expire", "300");
//		JSONObject httpClientProxyIp = HttpProxyClientLocat.getHttpClientProxyIp2808(path, map);
//		System.out.println(httpClientProxyIp.toString());
		
//		 try {
//		 JSONObject httpChilentProxyApi = HttpProxyClientLocat.getHttpChilentProxyApi();
//		 System.err.println(httpChilentProxyApi.toJSONString());
//		 } catch (InterruptedException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }{"data":[{"port":"4271","ip":""}],"status":0}
//		JSONObject connectHttpChilentStatus = HttpProxyClientLocat.connectHttpChilentStatus("121.61.171.31:3306");
//		System.err.println(connectHttpChilentStatus.toString());
	}
}
