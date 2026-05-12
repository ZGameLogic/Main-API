package com.zgamelogic.dashboard.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgamelogic.github.data.GithubRelease;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GithubRichRepositoryDTO {
    private long id;
    private List<GithubRichEnvironmentDTO> environments;
    private GithubRelease release;
    private Map<String, Long> languages;

    public GithubRichRepositoryDTO(long id) {
        this.id = id;
        environments = new ArrayList<>();
        languages = new HashMap<>();
        release = null;
    }

    public void addEnvironment(String name, String status){
        environments.add(new GithubRichEnvironmentDTO(name, status));
    }
}
