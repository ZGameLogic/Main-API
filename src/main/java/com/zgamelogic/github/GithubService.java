package com.zgamelogic.github;

import com.zgamelogic.github.data.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GithubService {
    private final RestClient restClient;
    private final CacheManager cacheManager;

    public GithubService(@Value("${github.token}") String githubToken, CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        restClient = RestClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
            .defaultHeader("X-GitHub-Api-Version", "2026-03-10")
            .build();
    }

    public void clearGithubCaches(){
        cacheManager.getCacheNames().stream()
            .filter(cacheName -> cacheName.startsWith("github"))
            .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
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

    @Cacheable("github projects")
    public List<GithubProject> getProjects(){
        GithubProject[] projects = restClient.get()
            .uri("/orgs/zgamelogic/projectsV2")
            .retrieve()
            .body(GithubProject[].class);
        return projects == null ? List.of() : List.of(projects);
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

    @Cacheable("github repository deployments")
    public List<GithubDeployment> getRepoDeployments(GithubRepository githubRepository, GithubEnvironment environment){
        URI uri = UriComponentsBuilder
            .fromUriString(githubRepository.deploymentsUrl())
            .queryParam("environment", environment.name())
            .build(true)
            .toUri();

        GithubDeployment[] deployments = restClient.get()
            .uri(uri)
            .retrieve()
            .body(GithubDeployment[].class);
        return deployments == null ? List.of() : List.of(deployments);
    }

    @Cacheable("github deployment status")
    public List<GithubDeploymentStatus> getDeploymentStatus(GithubDeployment githubDeployment){
        GithubDeploymentStatus[] statuses = restClient.get()
            .uri(githubDeployment.statusesUrl())
            .retrieve()
            .body(GithubDeploymentStatus[].class);
        return statuses == null ? List.of() : List.of(statuses);
    }

    @Cacheable("github repository environments")
    public List<GithubEnvironment> getRepoEnvironments(GithubRepository githubRepository){
        GithubEnvironmentResponse environments = restClient.get()
            .uri("/repos/zgamelogic/" +  githubRepository.name() + "/environments")
            .retrieve()
            .body(GithubEnvironmentResponse.class);
        return environments.environments();
    }
}
