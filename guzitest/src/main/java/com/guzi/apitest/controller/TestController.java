package com.guzi.apitest.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @RequestMapping("/test")
    public String index(HttpServletRequest request, Model model) {

        return "test";
    }
}
