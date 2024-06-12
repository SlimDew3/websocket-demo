package com.websocket.websocketdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//页面跳转
public class PageController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/loginerror")
    public String longinError(){
        return "loginerror";
    }
}
