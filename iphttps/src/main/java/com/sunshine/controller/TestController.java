package com.sunshine.controller;

import com.sunshine.http_proxy.job.Mkdirjob;
import com.sunshine.http_proxy.pool.ProxyHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangzi
 * @ClassName TestController
 * @Description :
 * @Date 2019/5/4 8:50
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "proxy")
public class TestController {
    @Autowired
    private Mkdirjob mkdirjob;
    @RequestMapping(value = "getProxyHost")
    public ProxyHost getProxyHost(){
        return mkdirjob.getProxyHost();
    }

    @RequestMapping(value = "getTest")
    public String getTest(String o){
        return "123123123";
    }
}
