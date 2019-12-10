package com.trilogyed.adminapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }
}
