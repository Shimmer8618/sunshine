package com.sunshine.http_proxy.utils;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {

    @Autowired
    private MapConfig mapConfig;
    @Autowired
    private HttpProxyConfig httpProxyConfig;

    @Test
    public void testMapConfig() {
        Map<String, Integer> limitSizeMap = mapConfig.getLimitSizeMap();
        if (limitSizeMap == null || limitSizeMap.size() <= 0) {
            System.out.println("limitSizeMap读取失败");
        } else {
            System.out.println("limitSizeMap读取成功，数据如下：");
            for (String key : limitSizeMap.keySet()) {
                System.out.println("key: " + key + ", value: " + limitSizeMap.get(key));
            }
        }
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Map<String, String> proxymap = httpProxyConfig.getHttpProxy();
        if (proxymap == null || proxymap.size() <= 0) {
            System.out.println("limitSizeMap读取失败");
        } else {
            System.out.println("limitSizeMap读取成功，数据如下：");
            for (String key : proxymap.keySet()) {
                System.out.println("key: " + key + ", value: " + proxymap.get(key));
            }
            System.err.println(httpProxyConfig.getHttpPath());
        }
    }

}
