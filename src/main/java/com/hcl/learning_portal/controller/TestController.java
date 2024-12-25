package com.hcl.learning_portal.controller;

import com.hcl.learning_portal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/route")
    public void test() {
        System.out.println("Inside the controller");
    }

}
