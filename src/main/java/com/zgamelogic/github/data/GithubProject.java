package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubProject(
        long id,
        long number,
        String title,
        String description,
        @JsonProperty("short_description")
        String shortDescription,
        @JsonProperty("public")
        boolean isPublic,
        GithubUser owner
) {}
