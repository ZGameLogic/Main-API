package com.zgamelogic.data.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class GithubRepository {

    private String id;
    private String name;
    private String html_url;
    private String url;
    private Date updated_at;
    private String description;

    @JsonProperty("private")
    private boolean privateRepo;
}
