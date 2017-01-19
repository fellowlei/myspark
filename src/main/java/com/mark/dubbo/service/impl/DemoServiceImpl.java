package com.mark.dubbo.service.impl;

import com.mark.dubbo.service.DemoService;

/**
 * Created by root on 17-1-11.
 */
public class DemoServiceImpl implements DemoService{
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
