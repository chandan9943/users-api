package com.sdet.auto.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //    @RequestMapping(method = RequestMethod.GET, path = "/helloworld")
    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello, welcome to my users-api!";
    }

    @GetMapping("/")
    public String home() {
        return "Hello, welcome to sdetAutomation's springboot app.  To see the swagger page please " +
                "add \"/swagger-ui.html\" to the end of the url path!";
    }
}
