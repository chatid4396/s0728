package com.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@RestController()
public class UserController {

    @Value("${server.port}")
    int serverPort;

    @RequestMapping("/user/{id}")
    public String selectById(@PathVariable("id") String id) {
        System.out.println(serverPort);
        return "hello" + id;
    }
}
