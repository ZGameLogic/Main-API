package com.zgamelogic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping({
        "/",
        "/projects"
    })
    private String frontEnd(){
        return "forward:/index.html";
    }
}
