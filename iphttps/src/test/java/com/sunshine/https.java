package com.sunshine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author xiangzi
 * @ClassName https
 * @Description :
 * @Date 2019/4/29 11:18
 * @Version 1.0
 **/
public class https {
    public static void main(String[] args) {
        Set set = new HashSet();
        int [] ports = new int[]{80,443,3306,6379,5521};
        do{
            String randomIp = getRandomIp();
            System.out.println(randomIp);
            for (int port : ports) {
                boolean bool = isHostConnectable(randomIp, port);
                if (bool) {
                    set.add(randomIp);
                }
            }
        }while (set.size()<=200);
    }

    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(host+":"+port);
        return true;
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
        int[][] range = { { 607649792, 608174079 },//36.56.0.0-36.63.255.255
                { 1038614528, 1039007743 },//61.232.0.0-61.237.255.255
                { 1783627776, 1784676351 },//106.80.0.0-106.95.255.255
                { 2035023872, 2035154943 },//121.76.0.0-121.77.255.255
                { 2078801920, 2079064063 },//123.232.0.0-123.235.255.255
                { -1950089216, -1948778497 },//139.196.0.0-139.215.255.255
                { -1425539072, -1425014785 },//171.8.0.0-171.15.255.255
                { -1236271104, -1235419137 },//182.80.0.0-182.92.255.255
                { -770113536, -768606209 },//210.25.0.0-210.47.255.255
                { -569376768, -564133889 }, //222.16.0.0-222.95.255.255
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
