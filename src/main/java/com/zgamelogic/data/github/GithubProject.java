package com.zgamelogic.data.github;

import lombok.Getter;

import java.util.Date;
import java.util.LinkedList;

@Getter
public class GithubProject {

    private final String name;
    private final String description;
    private final Date updatedAt;
    private final String url;
    private final LinkedList<GithubCommit> commits;

    public GithubProject(GithubRepository githubRepository, LinkedList<GithubCommit> commits){
        this.commits = commits;
        name = githubRepository.getName();
        description = githubRepository.getDescription();
        updatedAt = githubRepository.getUpdated_at();
        url = githubRepository.getHtml_url();
    }
}
