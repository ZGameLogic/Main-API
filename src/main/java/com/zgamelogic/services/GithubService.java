package com.zgamelogic.services;

import com.zgamelogic.data.github.GithubCommit;
import com.zgamelogic.data.github.GithubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;

@Slf4j
public abstract class GithubService {

    public static LinkedList<GithubRepository> listRepositories(String githubToken){
        String url = "https://api.github.com/orgs/zgamelogic/repos";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + githubToken);
        ResponseEntity<GithubRepository[]> response = restTemplate.exchange(url, HttpMethod.GET,  new HttpEntity<>(headers), GithubRepository[].class);
        LinkedList<GithubRepository> repositories = new LinkedList<>(Arrays.asList(response.getBody()));
        repositories.removeIf(GithubRepository::isPrivateRepo);
        return repositories;
    }

    public static LinkedList<GithubCommit> listCommits(GithubRepository githubRepository, String githubToken){
        String url = githubRepository.getUrl() + "/commits?per_page=10";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + githubToken);
        try {
            ResponseEntity<GithubCommit[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), GithubCommit[].class);
            return new LinkedList<>(Arrays.asList(response.getBody()));
        } catch (Exception e){
            return new LinkedList<>();
        }
    }
}
