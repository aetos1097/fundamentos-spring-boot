package com.fundamentos.springboot.fundamentos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping//accept all request by http lvl
    @ResponseBody//request a body
    public ResponseEntity<String> function(){//we need to return a request with this function
        return new ResponseEntity<>("Hello from controller", HttpStatus.OK);
    }
}
