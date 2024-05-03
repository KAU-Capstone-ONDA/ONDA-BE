package com.capstone.onda.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @RequestMapping("/sample")
    public String docker() {
        return "login fail";
    }

    @RequestMapping("/sample2")
    public String sample() {
        return "login success";
    }
}
