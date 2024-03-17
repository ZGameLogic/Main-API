package com.zgamelogic.controllers;

import com.zgamelogic.data.github.GithubCommit;
import com.zgamelogic.data.github.GithubProject;
import com.zgamelogic.services.GithubService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class GithubController {

    @Value("${github.token}")
    private String githubToken;

    private LinkedList<GithubProject> projects;

    @PostConstruct
    @Scheduled(cron = "0 */5 * * * *")
    private void updateProjects(){
        projects = new LinkedList<>();
        GithubService.listRepositories(githubToken).forEach(repo -> {
            LinkedList<GithubCommit> commits = GithubService.listCommits(repo, githubToken);
            projects.add(new GithubProject(repo, commits));
        });
    }

    @GetMapping("github/projects")
    private LinkedList<GithubProject> githubProjects(){
        return projects;
    }
}
