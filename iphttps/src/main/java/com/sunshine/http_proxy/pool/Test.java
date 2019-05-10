package com.sunshine.http_proxy.pool;


import com.sunshine.http_proxy.utils.HttpProxyConfig;

public class Test {
     
    public static ObjectPool pool = ObjectPool.getObjecPool(ProxyHost.class, new HttpProxyConfig());
    
    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(){
                public void run() {
                	ProxyHost obj = Test.pool.borrowObject();
                    System.out.println(obj.toString());
                    try {
                        Thread.sleep(96000);
                    } catch (Exception e) {
                    }
                    Test.pool.returnObject(obj);
                };
            }.start();
        }
    }
 
}
