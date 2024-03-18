package com.zgamelogic.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class HealthController {

    @GetMapping("health")
    private String healthCheck(){
        return "Healthy";
    }

    @PostConstruct
    private void postConst(){
        File here = new File("./");
        listFiles(here, 0);
    }

    private void listFiles(File dir, int indents){
        for(File f: dir.listFiles()){
            if(f.isDirectory()) {
                for (int i = 0; i < indents; i++) {
                    System.out.print("\t");
                }
                System.out.println(f.getName() + "<<<");
                listFiles(f, indents + 1);
            } else {
                for (int i = 0; i < indents; i++) {
                    System.out.print("\t");
                }
                System.out.println(f.getName());
            }
        }
    }
}
