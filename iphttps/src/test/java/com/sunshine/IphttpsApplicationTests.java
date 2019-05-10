package com.sunshine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IphttpsApplicationTests {

    @Test
    public void contextLoads() {
        HttpsProxy("https://www.baidu.com", "", "127.0.0.1", 80);
        HttpProxy("http://www.aseoe.com", "", "127.0.0.1", 80);
//		Set set = new HashSet();
//		int [] ports = new int[]{80,443,3306,6379,5521};
//		do{
//			String randomIp = getRandomIp();
//			System.out.println(randomIp);
//			for (int port : ports) {
//				boolean bool = isHostConnectable(randomIp, port);
//				if (bool) {
//					set.add(randomIp);
//					break;
//				}
//			}
//		}while (set.size()<=200);
//		System.out.println(set.toString());
    }

    public static String HttpsProxy(String url, String param, String proxy, int port) {
        HttpsURLConnection httpsConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            System.out.println("请求的URL========：" + urlClient);

            SSLContext sc = SSLContext.getInstance("SSL");
            // 指定信任https
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            //创建代理虽然是https也是Type.HTTP
            Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpsConn = (HttpsURLConnection) urlClient.openConnection(proxy1);

            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            // 设置通用的请求属性
            httpsConn.setRequestProperty("accept", "*/*");
            httpsConn.setRequestProperty("connection", "Keep-Alive");
            httpsConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            httpsConn.setDoOutput(true);
            httpsConn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpsConn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpsConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 断开连接
            httpsConn.disconnect();
            System.out.println("====result====" + result);
            System.out.println("返回结果：" + httpsConn.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

        return result;
    }

    public static String HttpProxy(String url, String param, String proxy, int port) {
        HttpURLConnection httpConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            System.out.println("请求的URL========：" + urlClient);
            //创建代理
            Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpConn = (HttpURLConnection) urlClient.openConnection(proxy1);
            // 设置通用的请求属性
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestProperty("connection", "Keep-Alive");
            httpConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 断开连接
            httpConn.disconnect();
            System.out.println("====result====" + result);
            System.out.println("返回结果：" + httpConn.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

        return result;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static boolean isHostConnectable(String host, int port) {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Socket socket = new Socket();
                    try {
                        socket.connect(new InetSocketAddress(host, port));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(host + ":" + port);
        return false;
    }

    public static boolean isHostReachable(String host, Integer timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getRandomIp() {
//ip范围
        int[][] range = {{607649792, 608174079},//36.56.0.0-36.63.255.255
                {1038614528, 1039007743},//61.232.0.0-61.237.255.255
                {1783627776, 1784676351},//106.80.0.0-106.95.255.255
                {2035023872, 2035154943},//121.76.0.0-121.77.255.255
                {2078801920, 2079064063},//123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},//139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},//171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},//182.80.0.0-182.92.255.255
                {-770113536, -768606209},//210.25.0.0-210.47.255.255
                {-569376768, -564133889}, //222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0]
                + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "."
                + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }
}
