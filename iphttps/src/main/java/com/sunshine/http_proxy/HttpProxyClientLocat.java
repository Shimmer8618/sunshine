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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sunshine.http_proxy.utils.DateUtil;
import com.sunshine.http_proxy.utils.HttpClientUtil;
import com.sunshine.http_proxy.utils.HttpProxyConfig;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HttpProxyClientLocat implements Runnable {
    public static Logger log = LoggerFactory.getLogger(HttpProxyClientLocat.class);
    private static final String BAIDU_PATH = "http://www.baidu.com";
    private static Map<String, String> hashMap = new Hashtable<String, String>();
    private static JSONObject json = new JSONObject();
    private static int[] ports = new int[]{80, 443, 8883, 4271, 8886, 8080, 8888, 8887, 8889, 8081};
    private static String expireTime, city, isp;
    private static int i = 0;

    /**
     * @param path
     * @param map
     * @return
     */
    public static JSONObject getHttpClientProxyIp2808(String path, Map<String, String> map) {
        List list = new ArrayList<HashMap<String, String>>();
        JSONObject json = new JSONObject();
        do {
            String result = HttpClientUtil.httpGet(path, map);
            log.info("获取ip信息：" + result);
            List<String> parseArray = JSON.parseArray(JSON.parseObject(result).getString("data"), String.class);
            for (String str : parseArray) {
                String ip = JSON.parseObject(str).getString("ip");
                String port = JSON.parseObject(str).getString("http_port_secured");
                // String id =
                // JSON.parseObject(result).getJSONArray("data").getJSONObject(0).getString("id");
                Map<String, Integer> map1 = new HashMap<String, Integer>();
                map1.put(ip, Integer.valueOf(port));
                checkProxyIp(map1, BAIDU_PATH);
            }
        } while ((i < 3) && (hashMap.size() < 1));
        list.add(hashMap);
        json.put("status", 0);
        json.put("data", list);
        return json;
    }

    /**
     * @param httpProxyConfig
     * @return
     */
    public static JSONObject getHttpClientProxyIpZhiMa8(HttpProxyConfig httpProxyConfig) {
        hashMap.clear();
        JSONObject json = new JSONObject();
        if (httpProxyConfig.getiPType() == 0) {
            try {
                json = getHttpChilentProxyApi();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (httpProxyConfig.getiPType() == 1) {
            json = getHttpClient(httpProxyConfig);
        } else if (httpProxyConfig.getiPType() == 2) {
            if (Math.random() > 0.3) {
                json = getHttpClient(httpProxyConfig);
            } else {
                try {
                    json = getHttpChilentProxyApi();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    private static JSONObject getHttpClient(HttpProxyConfig httpProxyConfig) {
        List list = new ArrayList<HashMap<String, String>>();
        do {
            log.info("发送Get请求：" + httpProxyConfig.getHttpPath());
            String result = HttpClientUtil.httpGet(httpProxyConfig.getHttpPath(), httpProxyConfig.getHttpProxy());
            log.info("获取ip信息：" + result);
            //String result =
            //"{\"code\":0,\"success\":true,\"msg\":\"0\",\"data\":[{\"ip\":\"121.61.171.31\",\"port\":4271,\"expire_time\":\"2019-05-02 23:55:26\",\"city\":\"湖北省荆州市\",\"isp\":\"电信\"}]}";
            log.info(result);
            List<String> parseArray = JSON.parseArray(JSON.parseObject(result).getString("data"), String.class);
            for (String str : parseArray) {
                String ip = JSON.parseObject(str).getString("ip");
                String port = JSON.parseObject(str).getString("port");
                expireTime = JSON.parseObject(str).getString("expire_time");
                isp = city = JSON.parseObject(str).getString("isp");
                city = JSON.parseObject(str).getString("city");
                // String id =
                // JSON.parseObject(result).getJSONArray("data").getJSONObject(0).getString("id");
                Map<String, Integer> map1 = new HashMap<String, Integer>();
                map1.put(ip, Integer.valueOf(port));
                checkProxyIp(map1, BAIDU_PATH);
            }
        } while ((i < 3) && (hashMap.size() < 1));
        list.add(hashMap);
        json.put("status", 0);
        json.put("data", list);
        return json;
    }

    /**
     * 单个代理IP有效检测
     *
     * @param ip
     * @param port
     */
    public void createIPAddress(String ip, int port) {
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
        } catch (MalformedURLException e) {
            log.info("url invalidate");
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
            log.info(Thread.currentThread().getName() + "  ip " + ip + " : " + port + "   is not aviable");// 异常IP
        }
        String s = convertStreamToString(in);
        //log.info(s);
        // log.info(s);http://ip.taobao.com/service/getIpInfo.php?ip=210.32.158.14
            synchronized (this){
                if (s.indexOf("html") > 0) {// 有效IP

                        log.info(Thread.currentThread().getName() + "   " + ip + ":" + port + " is ok");
                        hashMap.put("ip", ip);
                        hashMap.put("port", port + "");
                        String s1 = HttpClientUtil.getResult("http://ip.taobao.com/service/getIpInfo.php", "ip=" + ip, "utf-8");
                        //String[] split = JSON.parseObject(s1).getJSONArray("data").getJSONObject(0).getString("location")
                        //.split(" ");
                        JSONObject data = JSON.parseObject(JSON.parseObject(s1).getString("data"));
                        hashMap.put("ip", ip);
                        hashMap.put("port", port + "");
                        hashMap.put("creaTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
                        hashMap.put("expireTime", DateUtil.format(DateUtil.addMinute(new Date(), 30), DateUtil.TIME_PATTERN_DEFAULT));
                        if (StringUtils.equals(data.getString("region"), data.getString("city"))) {
                            hashMap.put("city", data.getString("region") + "市" + data.getString("city") + "市");
                        } else {
                            hashMap.put("city", data.getString("region") + "省" + data.getString("city") + "市");
                        }
                        hashMap.put("isp", data.getString("isp"));

                }
            }
    }

    public static JSONObject getHttpChilentProxyApi() throws InterruptedException {
        Vector vector = new Vector();
        log.info("获取免费ip");
        Vector<Thread> vec = new Vector<Thread>();
        HttpProxyClientLocat httptest2 = new HttpProxyClientLocat();
        int threadNumber = 320;
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread(httptest2);
            thread.start();
            vec.add(thread);
        }
        for (Thread thread : vec) {
            thread.join();
        }
        vector.add(hashMap);
        json.put("status", 0);
        json.put("data", vector);
        log.info(json.toString());
        return json;
    }

    @Override
    public void run() {
        while (hashMap.size() < 1) {
            String randomIp = getRandomIp();
            log.info(randomIp);
            for (int port : ports) {
                createIPAddress(randomIp, port);
            }
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

    /**
     * 批量代理IP有效检测
     *
     * @param proxyIpMap
     * @param reqUrl
     */
    public static void checkProxyIp(Map<String, Integer> proxyIpMap, String reqUrl) {
        log.info("检测ip是否有效");
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
                log.info(httpGet);
                statusCode = httpGet.indexOf("html");
            } catch (Exception e) {
                i++;
                log.error("ip " + proxyHost + " is not aviable");
            }
            Lock l = new ReentrantLock();
            l.lock();
            try {
                if (statusCode > 0) {
                    log.info(Thread.currentThread().getName() + "   " + proxyHost + ":" + proxyPort + " is ok");
                    System.out.format("%s:%s-->%s/n", proxyHost, proxyPort, statusCode);
                    hashMap.put("creaTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
                    hashMap.put("expireTime", expireTime);
                    hashMap.put("city", city);
                    hashMap.put("ip", proxyHost);
                    hashMap.put("port", proxyPort + "");
                }
            } finally {
                l.unlock();
            }
        }
    }

    public static JSONObject connectHttpChilentStatus(String http) {
        List list = new ArrayList<HashMap<String, String>>();
        log.info("开始校验" + http);
        JSONObject json = new JSONObject();
        if (null != http && "" != http) {
            String[] ips = http.split(",");
            for (String ip : ips) {
                if (null != ip && "" != ip) {
                    Map<String, Object> map = new HashMap<>();
                    String[] str = ip.split(":");
                    int statusCode = 0;
                    map.put("ip", str[0]);
                    map.put("port", str[1]);
                    try {
                        HttpClient httpClient = new HttpClient();
                        httpClient.getHostConfiguration().setProxy(str[0], Integer.valueOf(str[1]));
                        // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
                        int connectionTimeout = 10000;
                        // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
                        int soTimeout = 30000;
                        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
                        httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

                        String httpGet = HttpClientUtil.httpGet(BAIDU_PATH, new HashMap<>());
                        log.info(httpGet);
                        statusCode = httpGet.indexOf("html");
                    } catch (Exception e) {
                        map.put("status", 500);
                        map.put("msg", "连接超时");
                    }
                    if (statusCode > 0) {
                        map.put("status", 0);
                        map.put("msg", "连接成功");
                    }
                    list.add(map);
                }
            }
        }
        json.put("status", 0);
        json.put("data", list);
        return json;
    }

    public static String getRandomIp() {
        // ip范围
        int[][] range = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "."
                + Integer.toString(b[3]);

        return x;
    }

    public static void main(String[] args) {
//		Map<String, String> map = new HashMap<String, String>();
//		String path = "https://api.2808proxy.com/proxy/unify/get";
//		map.put("token", "A15Q4AYR900XTQYZHC28H4D07H7E8940");
//		map.put("amount", "1");
//		map.put("proxy_type", "http");
//		map.put("format", "json");
//		map.put("splitter", "rn");
//		map.put("expire", "300");
//		JSONObject httpClientProxyIp = getHttpClientProxyIp2808(path, map);
//		log.info(httpClientProxyIp.toString());

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
        JSONObject zhima = getHttpClientProxyIpZhiMa8(new HttpProxyConfig());
        log.info(zhima.toString());

        // try {
        // JSONObject httpChilentProxyApi = getHttpChilentProxyApi();
        // log.info(httpChilentProxyApi.toJSONString());
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}