package com.zgamelogic.dashboard.api;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class GithubRichRepositoryDTO {
    private long id;
    private List<GithubRichEnvironmentDTO> environments;

    public GithubRichRepositoryDTO(long id) {
        this.id = id;
        environments = new ArrayList<>();
    }

    public void addEnvironment(String name, String status){
        environments.add(new GithubRichEnvironmentDTO(name, status));
    }
}
