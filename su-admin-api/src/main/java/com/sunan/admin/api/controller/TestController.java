package com.sunan.admin.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/test")
    public Object test(){
        return "test";
    }
    @RequestMapping("/test1")
    public Object test1(){
        return "test1";
    }
}
