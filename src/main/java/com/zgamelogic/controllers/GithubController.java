package com.zgamelogic.controllers;

import com.zgamelogic.data.github.GithubProject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController("github")
@PropertySource("file:application.properties")
public class GithubController {

    @Value("${github.token}")
    private String githubToken;

    @GetMapping("projects")
    private LinkedList<GithubProject> githubProjects(){
        return null;
    }
}
