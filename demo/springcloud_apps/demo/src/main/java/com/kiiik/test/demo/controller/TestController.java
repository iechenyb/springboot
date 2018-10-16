package com.kiiik.test.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/helloworld")
    public String helloworld() {
        return "helloworld";
    }

    @GetMapping("/test1")
    public String test1() {
        return "权限1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "权限2";
    }
}
