package com.zgamelogic.github;

import com.zgamelogic.github.data.GithubProject;
import com.zgamelogic.github.data.GithubRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GithubService {
    private final RestClient restClient;

    public GithubService(@Value("${github.token}") String githubToken) {
        restClient = RestClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
            .defaultHeader("X-GitHub-Api-Version", "2026-03-10")
            .build();
    }

    @Cacheable("github repositories")
    public List<GithubRepository> getRepos(){
        GithubRepository[] repos = restClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/orgs/zgamelogic/repos")
                    .queryParam("per_page", 100)
                .build()
            )
            .retrieve()
            .body(GithubRepository[].class);
        return repos == null ? List.of() : List.of(repos);
    }

    @Cacheable("github repository languages")
    public Map<String, Long> getRepoLanguages(GithubRepository githubRepository){
        Map<String, Long> languages = restClient.get()
            .uri(githubRepository.languagesUrl())
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
        });
        return languages == null ? new HashMap<>() : languages;
    }

    @Cacheable("github projects")
    public List<GithubProject> getProjects(){
        GithubProject[] projects = restClient.get()
            .uri("/orgs/zgamelogic/projectsV2")
            .retrieve()
            .body(GithubProject[].class);
        return projects == null ? List.of() : List.of(projects);
    }
}
